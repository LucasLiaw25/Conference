package com.liaw.dev.Conference.validation;

import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.exceptions.conference.ConferenceExistException;
import com.liaw.dev.Conference.exceptions.conference.ConferenceNotFoundException;
import com.liaw.dev.Conference.repository.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConferenceValidation {

    private final ConferenceRepository repository;

    public void validateConferenceId(Long id){
        Optional<Conference> conference = repository.findById(id);
        if (conference.isEmpty()){
            throw new ConferenceNotFoundException("Conferência não encontrada");
        }
    }

    public void validateCreateConference(Conference conference){
        Optional<Conference> conference1 = repository.findByNameAndStartDate(conference.getName(), conference.getStartDate());
        if (conference1.isPresent()){
            throw new ConferenceExistException("Conferência já cadastrada");
        }
    }

}
