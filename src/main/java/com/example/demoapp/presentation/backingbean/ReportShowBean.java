package com.example.demoapp.presentation.backingbean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.repository.ReportRepository;
import com.example.demoapp.type.Status;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class ReportShowBean implements Serializable {
    private Integer reportId;
    private String title;
    private String detail;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ExternalContext externalContext;

    @PostConstruct
    public void initReport() {
        Map<String, String> params = externalContext.getRequestParameterMap();
        reportId = Integer.parseInt(params.get("reportId"));
        fetchReport();
    }

    public void apply() {
        reportRepository.apply(reportId);
        fetchReport();
    }

    public void approve() {
        reportRepository.approve(reportId);
        fetchReport();
    }

    public void remand() {
        reportRepository.remand(reportId);
        fetchReport();
    }

    private void fetchReport() {
        ReportDto report = reportRepository.findById(reportId);
        if (report != null) {
            title = report.getTitle();
            detail = report.getDetail();
            status = report.getStatus();
            createdAt = report.getCreatedAt();
            updatedAt = report.getUpdatedAt();
        }
    }
}
