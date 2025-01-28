package com.example.demoapp.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.demoapp.dto.ReportDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportRepository {
    private List<ReportDto> reports = new ArrayList<>();
    private Integer maxReportId = 0;

    public List<ReportDto> findAll() {
        return reports;
    }

    public ReportDto findById(Integer id) {
        return reports.get(id);
    }
    
    public void create(ReportDto report) {
        report.setReportId(maxReportId);
        maxReportId += 1;

        reports.add(report);
    }
}
