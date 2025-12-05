package com.liaw.dev.Conference.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Conference.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_conference")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private State state;
    private String city;
    private String local;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer minimumAge;
    private String code;
    private BigDecimal registrationFee;

    @JsonIgnore
    @OneToMany(mappedBy = "conference")
    private List<Payment> payments;

    @JsonIgnore
    @OneToMany(mappedBy = "conference")
    private List<User> users;
    private Boolean housing;

}
