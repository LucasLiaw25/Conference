package com.liaw.dev.Conference.mapper;

import com.liaw.dev.Conference.dto.PaymentDTO;
import com.liaw.dev.Conference.dto.UserResponse;
import com.liaw.dev.Conference.entity.Payment;
import com.liaw.dev.Conference.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final UserMapper userMapper;

        public PaymentDTO toDTO(Payment payment){
            return new PaymentDTO(
                    payment.getId(),
                    payment.getTxid(),
                    payment.getAmount(),
                    userMapper.toUserResponse(payment.getUser()),
                    payment.getConference(),
                    payment.getPaymentStatus(),
                    payment.getPaymentLink()
            );
        }
    public Payment toEntity(PaymentDTO payment){
        return new Payment(
                payment.getId(),
                payment.getTxid(),
                payment.getAmount(),
                new User(
                        payment.getUser().getId(),
                        payment.getUser().getName(),
                        payment.getUser().getAge(),
                        payment.getUser().getState(),
                        payment.getUser().getCity(),
                        payment.getUser().getCpf(),
                        payment.getUser().getEmail(),
                        payment.getUser().getRole()
                ),
                payment.getConference(),
                payment.getPaymentStatus(),
                payment.getPaymentLink()
        );
    }

    public List<PaymentDTO> toPaymentDTOList(List<Payment> payments){
            return payments.stream().map(this::toDTO).toList();
    }

}
