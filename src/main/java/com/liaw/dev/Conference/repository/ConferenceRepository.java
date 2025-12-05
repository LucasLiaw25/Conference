package com.liaw.dev.Conference.repository;

import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    List<Conference> findByState(State state);
    Optional<Conference> findByCode(String code);
    Optional<Conference> findByNameAndStartDate(String name, LocalDate startDate);
}
