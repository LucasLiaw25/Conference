package com.liaw.dev.Conference.dto;

import com.liaw.dev.Conference.enums.Role;
import lombok.Builder;

@Builder
public record JWTDetails(Long id, String name, String email, String role) {
}
