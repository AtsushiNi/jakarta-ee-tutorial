package com.example.demoapp.presentation.backingbean;

import java.io.IOException;
import java.util.List;

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
public class UserListBean {
    private List<UserDto> users;
    private UserDto selectedUser;

    @Inject
    private UserRepository UserRepository;

    @Inject
    private ExternalContext externalContext;

    @PostConstruct
    public void init() {
        users = UserRepository.findAll();
    }

    public void navigateToShowPage() throws IOException {
        String redirectUrl = externalContext.getRequestContextPath() + "/userShow.xhtml?userId=" + selectedUser.getUserId();
        externalContext.redirect(redirectUrl);
    }
}
