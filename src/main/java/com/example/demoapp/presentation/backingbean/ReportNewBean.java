package com.example.demoapp.presentation.backingbean;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.repository.ReportRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
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

    @Inject
    private ReportRepository reportRepository;

    public String create() {
        ReportDto newReport = new ReportDto();
        newReport.setTitle(title);
        newReport.setDetail(detail);
        reportRepository.create(newReport);
        return "/reportList.xhtml?faces-redirect=true";
    }
}
