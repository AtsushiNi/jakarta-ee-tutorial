package com.example.demoapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String hashedPassword;
    private byte[] imageData;
    private List<ReportDto> reports;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
