package com.example.demoapp.infrastructure.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.converter.UserConverter;
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
    private UserConverter userConverter;

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
        return userStream.map(userConverter::toDto).toList();
    }

    public UserDto findById(Integer id) {
        UserEntity user = em.find(UserEntity.class, id);
        return userConverter.toDtoWithReports(user);
    }

    public UserDto findByEmail(String email) {
        TypedQuery<UserEntity> query = em.createQuery("select u from UserEntity u where u.email = :email", UserEntity.class);
        query.setParameter("email", email);
        List<UserEntity> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return userConverter.toDtoWithHashedPassword(users.get(0));
        }
    }

    public void create(UserDto user) {
        UserEntity entity = userConverter.toEntity(user);

        String hashedPassword = passwordHash.generate(user.getPassword().toCharArray());
        entity.setHashedPassword(hashedPassword);

        entity.setImageData(getDefaultUserImage());

        em.persist(entity);
    }

    public void update(UserDto user) {
        UserEntity userEntity = em.find(UserEntity.class, user.getUserId());

        userEntity.setImageData(user.getImageData());

        em.merge(userEntity);
    }

    private byte[] getDefaultUserImage() {
        try (InputStream inputStream = UserRepository.class.getClassLoader().getResourceAsStream("default-user.png");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: default-user.png");
            }
            byte[] buffer = new byte[1024];
            while(true) {
                int length = inputStream.read(buffer);
                if(length < 0) {
                    break;
                }
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
