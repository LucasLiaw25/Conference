package com.liaw.dev.Conference.repository;

import com.liaw.dev.Conference.entity.Payment;
import com.liaw.dev.Conference.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTxid(String txid);
    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
}
