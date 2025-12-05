package com.liaw.dev.Conference.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Conference.entity.Payment;
import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.enums.State;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ConferenceDTO {
    private Long id;
    private String name;
    private State state;
    private String city;
    private String local;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer minimumAge;
    private String code;
    private BigDecimal registrationFee;
    private List<PaymentDTO> paymentsDTO;
    private List<Payment> payments;
    private List<UserResponse> userResponses;
    private List<User> users;
    private Boolean housing;

    public ConferenceDTO(Long id, String name, State state, String city, String local, LocalDate startDate, LocalDate endDate, Integer minimumAge, String code, BigDecimal registrationFee, List<PaymentDTO> paymentsDTO, List<Payment> payments, List<UserResponse> userResponses, List<User> users, Boolean housing) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.city = city;
        this.local = local;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minimumAge = minimumAge;
        this.code = code;
        this.registrationFee = registrationFee;
        this.paymentsDTO = paymentsDTO;
        this.userResponses = userResponses;
        this.users = users;
        this.housing = housing;
    }

    public ConferenceDTO(Long id, String name, State state, String city, String local, LocalDate startDate, LocalDate endDate, Integer minimumAge, String code, BigDecimal registrationFee, List<PaymentDTO> paymentsDTO, List<UserResponse> userResponses, Boolean housing) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.city = city;
        this.local = local;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minimumAge = minimumAge;
        this.code = code;
        this.registrationFee = registrationFee;
        this.paymentsDTO = paymentsDTO;
        this.userResponses = userResponses;
        this.housing = housing;
    }
}
