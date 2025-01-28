package com.example.demoapp.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.demoapp.dto.ReportDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportRepository {
    private List<ReportDto> reports = new ArrayList<>();

    public List<ReportDto> findAll() {
        return reports;
    }

    public void create(ReportDto report) {
        reports.add(report);
    }
}
