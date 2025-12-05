package com.liaw.dev.Conference.dto;

import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private String txid;
    private BigDecimal amount;
    private UserResponse user;
    private Conference conference;
    private PaymentStatus paymentStatus;
    private String paymentLink;
}
