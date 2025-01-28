package com.example.demoapp.security;

import java.util.Set;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class DatabaseIdentityStore implements IdentityStore {
    
    @Inject
    private UserRepository userRepository;


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

        if ((new String(inputPassword)).equals(user.getPassword())) {
            Set<String> roles = Set.of("USER");
            return new CredentialValidationResult(new CallerPrincipal(user.getEmail()), roles);
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return validationResult.getCallerGroups();
    }
}
