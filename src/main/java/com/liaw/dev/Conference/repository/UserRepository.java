package com.liaw.dev.Conference.repository;

import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByState(State state);
    Optional<User> findByEmailAndCpf(String email, String cpf);
    Optional<User> findByEmail(String email);
}
