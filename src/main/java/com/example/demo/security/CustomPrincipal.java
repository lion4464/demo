package com.example.demo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomPrincipal implements Principal {
    private UUID userId;
    private String username;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
