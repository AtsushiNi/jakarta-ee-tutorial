package com.example.demoapp.presentation.backingbean;

import java.io.Serializable;
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
            status = report.getStatus();
        }
    }

    public void apply() {
        reportRepository.apply(reportId);
        status = Status.C02_WAIT_REVIEW;
    }

    public void approve() {
        reportRepository.approve(reportId);
        status = Status.C03_COMPLETED;
    }

    public void remand() {
        reportRepository.remand(reportId);
        status = Status.C01_CREATING;
    }
}
