package com.example.demoapp.presentation.backingbean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class ReportNewBean {

    @NotBlank(message = "タイトルは空白にできません")
    private String title;
    private String detail;

}
