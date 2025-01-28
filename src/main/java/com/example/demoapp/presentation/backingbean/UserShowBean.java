package com.example.demoapp.presentation.backingbean;

import java.io.Serializable;
import java.util.Map;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class UserShowBean implements Serializable {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ExternalContext externalContext;

    @PostConstruct
    public void initUser() {
        Map<String, String> params = externalContext.getRequestParameterMap();
        userId = Integer.parseInt(params.get("userId"));

        UserDto user = userRepository.findById(userId);
        if (user != null) {
            firstName = user.getFirstName();
            lastName = user.getLastName();
            email = user.getEmail();
        }
    }
}
