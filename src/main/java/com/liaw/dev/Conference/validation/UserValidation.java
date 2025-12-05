package com.liaw.dev.Conference.validation;

import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.exceptions.user.UserExistException;
import com.liaw.dev.Conference.exceptions.user.UserNotFoundException;
import com.liaw.dev.Conference.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository repository;

    public void validateUserId(Long id){
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException("Usuário não encontrado");
        }
    }

    public void createUserValidator(User user){
        Optional<User> user1 = repository.findByEmailAndCpf(user.getEmail(), user.getCpf());
        if (user1.isPresent()){
            throw new UserExistException("Usuário com email: " + user.getEmail() + " já cadastrado.");
        }

    }

}
