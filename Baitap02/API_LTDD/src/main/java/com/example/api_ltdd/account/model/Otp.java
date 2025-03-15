package com.example.api_ltdd.account.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otpCode;
    private LocalDateTime expiryTime;
    private boolean isVerified;
}