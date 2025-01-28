package com.example.demoapp.infrastructure.converter;
import java.util.List;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.entity.UserEntity;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UserConverter {

    @Inject
    private ReportConverter reportConverter;

    public UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setImageData(userEntity.getImageData());

        return userDto;
    }

    public UserDto toDtoWithHashedPassword(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = toDto(userEntity);

        userDto.setHashedPassword(userEntity.getHashedPassword());

        return userDto;
    }

    public UserDto toDtoWithReports(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = toDto(userEntity);

        List<ReportDto> reports = userEntity.getReports().stream().map(reportConverter::toDto).toList();
        userDto.setReports(reports);

        return userDto;
    }

    public UserEntity toEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());

        return userEntity;
    }
}
