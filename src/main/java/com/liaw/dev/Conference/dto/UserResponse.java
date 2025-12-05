package com.liaw.dev.Conference.dto;

import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.enums.Role;
import com.liaw.dev.Conference.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private Integer age;
    private State state;
    private String city;
    private String cpf;
    private String email;
    private Role role;
}
