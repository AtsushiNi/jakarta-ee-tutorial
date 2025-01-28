package com.example.demoapp.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
public class DatabaseIdentityStore implements IdentityStore {
    
    @Inject
    private UserRepository userRepository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    public void initPasswordHash() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (!(credential instanceof UsernamePasswordCredential)) {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }

        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
        String inputEmail = usernamePasswordCredential.getCaller();
        char[] inputPassword = usernamePasswordCredential.getPassword().getValue();

        UserDto user = userRepository.findByEmail(inputEmail);
        if (user == null) {
            return CredentialValidationResult.INVALID_RESULT;
        }

        if (passwordHash.verify(inputPassword, user.getHashedPassword())) { 
            Set<String> roles = Set.of("USER");
            return new CredentialValidationResult(new CustomCallerPrincipal(user), roles);
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return validationResult.getCallerGroups();
    }
}
