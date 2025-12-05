package com.liaw.dev.Conference.service;

import com.liaw.dev.Conference.dto.UserDTO;
import com.liaw.dev.Conference.dto.UserResponse;
import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.enums.Role;
import com.liaw.dev.Conference.enums.State;
import com.liaw.dev.Conference.mapper.UserMapper;
import com.liaw.dev.Conference.repository.UserRepository;
import com.liaw.dev.Conference.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final UserValidation validation;
    private final UserRepository repository;

    public UserDTO createUser(UserDTO dto){
        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        validation.createUserValidator(user);
        repository.save(user);
        return mapper.toDTO(user);
    }

    public List<UserResponse> listUser(){
        List<User> users = repository.findAll();
        return users.stream().map(mapper::toUserResponse).toList();
    }

    public List<UserResponse> listByState(State state){
        List<User> users = repository.findByState(state);
        return users.stream().map(mapper::toUserResponse).toList();
    }

    public UserResponse findById(Long id){
        validation.validateUserId(id);
        User user = repository.findById(id).get();
        return mapper.toUserResponse(user);
    }

    public UserResponse updateUser(Long id, UserDTO dto){
        validation.validateUserId(id);
        User user = mapper.toEntity(dto);
        user.setId(id);
        repository.save(user);
        return mapper.toUserResponse(user);
    }

    public void deleteUser(Long id){
        validation.validateUserId(id);
        repository.deleteById(id);
    }

    public void givAdmin(Long adminId, Long userId){
        validation.validateUserId(adminId);
        validation.validateUserId(userId);
        User admin = repository.findById(adminId).get();
        User user = repository.findById(userId).get();
        if (user.getRole() == Role.USER && admin.getRole() == Role.ADMIN){
            user.setRole(Role.ADMIN);
            repository.save(user);
        }
    }


}
