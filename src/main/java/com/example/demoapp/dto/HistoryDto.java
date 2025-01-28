package com.example.demoapp.dto;

import java.time.LocalDateTime;

import com.example.demoapp.type.ActionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoryDto {
    private ActionType actionType;
    private LocalDateTime createdAt;
    private UserDto user;
}
