package com.liaw.dev.Conference.controller;

import com.liaw.dev.Conference.dto.PaymentDTO;
import com.liaw.dev.Conference.dto.PaymentRequest;
import com.liaw.dev.Conference.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/payments/")
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentRequest request){
        PaymentDTO payment = service.makeEnrollment(request.conferenceCode(), request.userId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(payment);
    }

}
