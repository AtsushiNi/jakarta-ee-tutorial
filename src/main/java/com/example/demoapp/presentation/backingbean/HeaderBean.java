package com.example.demoapp.presentation.backingbean;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.Principal;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.security.CustomCallerPrincipal;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Named
@RequestScoped
@Getter
public class HeaderBean {
    
    private UserDto loginUser;

    @Inject
    private HttpServletRequest httpServletRequest;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;

    @PostConstruct
    public void fetchLoginUser() {
        Principal principal = securityContext.getCallerPrincipal();
        if (principal instanceof CustomCallerPrincipal) {
            loginUser = ((CustomCallerPrincipal) principal).getUser();
        }
    }

    public void logout() {
        try {
            httpServletRequest.logout();
        } catch(ServletException e) {
            throw new RuntimeException(e);
        }

        HttpSession session = (HttpSession) externalContext.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        try {
            String currentPath = ((HttpServletRequest)externalContext.getRequest()).getRequestURI();
            externalContext.redirect(currentPath);
        } catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
