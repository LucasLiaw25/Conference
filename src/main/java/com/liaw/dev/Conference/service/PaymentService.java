package com.liaw.dev.Conference.service;

import com.liaw.dev.Conference.dto.PaymentDTO;
import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.entity.Payment;
import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.enums.PaymentStatus;
import com.liaw.dev.Conference.exceptions.conference.ConferenceNotFoundException;
import com.liaw.dev.Conference.exceptions.payment.PaymentNotFoundException;
import com.liaw.dev.Conference.exceptions.user.UserNotFoundException;
import com.liaw.dev.Conference.mapper.PaymentMapper;
import com.liaw.dev.Conference.pix.PixService;
import com.liaw.dev.Conference.repository.ConferenceRepository;
import com.liaw.dev.Conference.repository.PaymentRepository;
import com.liaw.dev.Conference.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ConferenceRepository repository;
    private final UserRepository userRepository;
    private final PixService pixService;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    public PaymentDTO makeEnrollment(String conferenceCode, Long userId){
        Conference conference = repository.findByCode(conferenceCode)
                .orElseThrow(()-> new ConferenceNotFoundException("Conferência não encontrada"));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado"));

        String value = conference.getRegistrationFee().setScale(2, RoundingMode.HALF_UP).toString();
        JSONObject pixCharge = pixService.createPixCharge(value, user.getName(), user.getCpf());
        String txid = pixCharge.getString("txid");
        String locationCode = extractLocationCode(pixCharge);
        String paymentLink = buildPaymentLink(locationCode);

        Payment payment = new Payment();
        payment.setConference(conference);
        payment.setUser(user);
        payment.setTxid(txid);
        payment.setAmount(conference.getRegistrationFee());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentLink(paymentLink);
        Payment create_payment = paymentRepository.save(payment);

        return paymentMapper.toDTO(create_payment);
    }

    private String extractLocationCode(JSONObject pixCharge) {
        try {
            String location = pixCharge.optString("location", "");
            if (!location.isEmpty()) {
                String[] parts = location.split("/");
                return parts[parts.length - 1];
            }
        } catch (Exception e) {
            return pixCharge.getString("txid");
        }
        return "Erro ao extrair o código da localização";
    }

    private String buildPaymentLink(String locationCode){
        return "https://pix.sejaefi.com.br/cob/pagar/" + locationCode;
    }

    @Transactional
    public String processPayment(String txid){
        String status = pixService.checkPaymentStatus(txid);
        if ("CONCLUIDA".equalsIgnoreCase(status)){
            Payment payment = paymentRepository.findByTxid(txid)
                    .orElseThrow(()-> new PaymentNotFoundException("Pagamento não encontrado"));
            if (payment.getPaymentStatus() == PaymentStatus.PENDING){
                User user = userRepository.findById(payment.getUser().getId())
                        .orElseThrow(()-> new UserNotFoundException("Usuário não encontrado"));
                Conference conference = repository.findById(payment.getConference().getId())
                        .orElseThrow(()-> new ConferenceNotFoundException("Conferência não encontrada"));

                payment.setPaymentStatus(PaymentStatus.COMPLETED);
                user.getPayments().add(payment);
                user.setConference(conference);
                conference.getUsers().add(user);
                conference.getPayments().add(payment);

                paymentRepository.save(payment);
                repository.save(conference);
                userRepository.save(user);

                return "Pagamento processado com sucesso.";
            }else {
                return "Pagamento já processado";
            }
        }
        return "Status de pagamento não concluído.";
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void checkPaymentStatus(){
        System.out.println("-----Começando a análise de pagamentos pendentes-----");
        List<Payment> payments = paymentRepository.findByPaymentStatus(PaymentStatus.PENDING);
        for (Payment payment : payments){
            processPayment(payment.getTxid());
        }
    }

}
