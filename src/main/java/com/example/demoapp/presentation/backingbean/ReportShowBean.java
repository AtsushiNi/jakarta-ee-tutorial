package com.example.demoapp.presentation.backingbean;

import java.util.Map;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.repository.ReportRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class ReportShowBean {
    private Integer reportId;
    private String title;
    private String detail;

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ExternalContext externalContext;

    @PostConstruct
    public void fetchReport() {
        Map<String, String> params = externalContext.getRequestParameterMap();
        reportId = Integer.parseInt(params.get("reportId"));
        ReportDto report = reportRepository.findById(reportId);
        if (report != null) {
            title = report.getTitle();
            detail = report.getDetail();
        }
    }
}
