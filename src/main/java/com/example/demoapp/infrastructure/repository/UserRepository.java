package com.example.demoapp.infrastructure.repository;

import java.util.List;
import java.util.stream.Stream;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.entity.UserEntity;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

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
        entity.setPassword(user.getPassword());

        em.persist(entity);
    }

    static private UserDto convertToDto(UserEntity entity) {
        UserDto user = new UserDto();
        user.setUserId(entity.getUserId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());

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
