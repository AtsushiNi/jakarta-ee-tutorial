package com.example.demoapp.security;

import com.example.demoapp.dto.UserDto;

import jakarta.security.enterprise.CallerPrincipal;
import lombok.Getter;

@Getter
public class CustomCallerPrincipal extends CallerPrincipal {
    private final UserDto user;

    public CustomCallerPrincipal(UserDto user) {
        super(user.getEmail());
        this.user = user;
    }
}
