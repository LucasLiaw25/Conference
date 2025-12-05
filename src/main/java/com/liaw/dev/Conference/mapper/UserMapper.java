package com.liaw.dev.Conference.mapper;

import com.liaw.dev.Conference.dto.UserDTO;
import com.liaw.dev.Conference.dto.UserResponse;
import com.liaw.dev.Conference.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getState(),
                user.getCity(),
                user.getCpf(),
                user.getEmail(),
                user.getPassword(),
                user.getConference(),
                user.getPayments(),
                user.getRole()
        );
    }

    public List<UserResponse> toUserResponseList(List<User> users){
        return users.stream().map(this::toUserResponse).toList();
    }

    public UserResponse toUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getState(),
                user.getCity(),
                user.getCpf(),
                user.getEmail(),
                user.getRole()
        );
    }

    public User toEntity(UserDTO user){
        return new User(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getState(),
                user.getCity(),
                user.getCpf(),
                user.getEmail(),
                user.getPassword(),
                user.getConference(),
                user.getPayments(),
                user.getRole()
        );
    }

}
