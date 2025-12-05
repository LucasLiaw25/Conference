package com.liaw.dev.Conference.service;

import com.liaw.dev.Conference.dto.ConferenceDTO;
import com.liaw.dev.Conference.dto.PaymentDTO;
import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.entity.Payment;
import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.enums.PaymentStatus;
import com.liaw.dev.Conference.enums.State;
import com.liaw.dev.Conference.exceptions.conference.ConferenceNotFoundException;
import com.liaw.dev.Conference.mapper.ConferenceMapper;
import com.liaw.dev.Conference.mapper.PaymentMapper;
import com.liaw.dev.Conference.pix.PixService;
import com.liaw.dev.Conference.repository.ConferenceRepository;
import com.liaw.dev.Conference.repository.PaymentRepository;
import com.liaw.dev.Conference.repository.UserRepository;
import com.liaw.dev.Conference.validation.ConferenceValidation;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ConferenceService {

    private final ConferenceMapper mapper;
    private final PaymentMapper paymentMapper;
    private final ConferenceValidation validation;
    private final PixService pixService;
    private final PaymentRepository paymentRepository;
    private final ConferenceRepository repository;
    private final UserRepository userRepository;

    public ConferenceDTO createConference(ConferenceDTO dto){
        Conference conference = mapper.toEntity(dto);
        conference.setCode(generateCode());
        validation.validateCreateConference(conference);
        repository.save(conference);
        return mapper.toDTO(conference);
    }

    public List<ConferenceDTO> listConference(){
        List<Conference> conferences = repository.findAll();
        return conferences.stream().map(mapper::toDTO).toList();
    }

    public ConferenceDTO findById(Long id){
        validation.validateConferenceId(id);
        Conference conference = repository.findById(id).get();
        return mapper.toDTO(conference);
    }

    public List<ConferenceDTO> findByState(State state){
        List<Conference> conference = repository.findByState(state);
        return conference.stream().map(mapper::toDTO).toList();
    }

    public ConferenceDTO findByCode(String code){
        Conference conference = repository.findByCode(code)
                .orElseThrow( ()-> new ConferenceNotFoundException("Conferência não encontrada"));
        return mapper.toDTO(conference);
    }

    public ConferenceDTO updateConference(Long id, ConferenceDTO dto){
        validation.validateConferenceId(id);
        Conference conference = mapper.toEntity(dto);
        conference.setId(id);
        repository.save(conference);
        return mapper.toDTO(conference);
    }

    public void deleteConference(Long id){
        validation.validateConferenceId(id);
        repository.deleteById(id);
    }


    public String generateCode(){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        final Integer num1 = random.nextInt(10);
        final Integer num2 = random.nextInt(10);
        final Integer num3 = random.nextInt(10);
        String code = String.valueOf(alphabet.charAt(random.nextInt(alphabet.length())))
                + String.valueOf(alphabet.charAt(random.nextInt(alphabet.length())))
                + String.valueOf(alphabet.charAt(random.nextInt(alphabet.length())))
                + num1 + num2 + num3;
        return code;
    }

}
