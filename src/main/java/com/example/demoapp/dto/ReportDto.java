package com.example.demoapp.dto;

import com.example.demoapp.type.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportDto {
    private Integer reportId;
    private String title;
    private String detail; 
    private Status status;
}
