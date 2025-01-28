package com.example.demoapp.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.entity.UserEntity;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

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

    public List<UserDto> findAll() {
        Stream<UserEntity> userStream = em.createQuery("select u from UserEntity u", UserEntity.class).getResultStream();
        return userStream.map(UserRepository::convertToDto).toList();
    }

    public UserDto findById(Integer id) {
        UserEntity user = em.find(UserEntity.class, id);
        return convertToDto(user);
    }

    public UserDto findByEmail(String email) {
        TypedQuery<UserEntity> query = em.createQuery("select u from UserEntity u where u.email = :email", UserEntity.class);
        query.setParameter("email", email);
        List<UserEntity> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return convertToDto(users.get(0));
        }
    }

    public void create(UserDto user) {
        UserEntity entity = convertToEntity(user);

        String hashedPassword = passwordHash.generate(user.getPassword().toCharArray());
        entity.setHashedPassword(hashedPassword);

        em.persist(entity);
    }

    static private UserDto convertToDto(UserEntity entity) {
        UserDto user = new UserDto();
        user.setUserId(entity.getUserId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setHashedPassword(entity.getHashedPassword());

        return user;
    }

    static private UserEntity convertToEntity(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        return user;
    }
}
