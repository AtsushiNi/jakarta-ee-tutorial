package com.example.demoapp.presentation.backingbean;

import java.util.List;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.repository.ReportRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class ReportListBean {
    private List<ReportDto> reports;

    @Inject
    private ReportRepository reportRepository;

    @PostConstruct
    public void init() {
        reports = reportRepository.findAll();
    }
}
