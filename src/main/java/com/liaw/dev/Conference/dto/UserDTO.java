package com.liaw.dev.Conference.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.entity.Payment;
import com.liaw.dev.Conference.enums.Role;
import com.liaw.dev.Conference.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "Campo Nome é obrigatorio")
    private String name;
    @NotNull(message = "Campo Idade é obrigatorio")
    private Integer age;
    private State state;
    @NotBlank(message = "Campo Cidade é obrigatorio")
    private String city;
    @NotBlank(message = "Campo CPF é obrigatorio")
    private String cpf;
    @NotBlank(message = "Campo Email é obrigatorio")
    private String email;
    @NotBlank(message = "Campo Senha é obrigatorio")
    private String password;
    private Conference conference;
    private List<Payment> payments;
    private Role role;

}
