package com.example.demoapp.infrastructure.converter;

import java.util.List;

import com.example.demoapp.dto.HistoryDto;
import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.entity.ReportEntity;
import com.example.demoapp.infrastructure.entity.UserEntity;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ReportConverter {

    @Inject
    private UserConverter userConverter;

    public ReportDto toDto(ReportEntity reportEntity) {
        ReportDto reportDto = new ReportDto();
        reportDto.setReportId(reportEntity.getReportId());
        reportDto.setTitle(reportEntity.getTitle());
        reportDto.setDetail(reportEntity.getDetail());
        reportDto.setStatus(reportEntity.getStatus());
        reportDto.setCreatedAt(reportEntity.getCreatedAt());
        reportDto.setUpdatedAt(reportEntity.getUpdatedAt());

        reportDto.setCreator(userConverter.toDto(reportEntity.getCreator()));

        return reportDto;
    }

    public ReportDto toDtoWithHistories(ReportEntity reportEntity) {
        ReportDto reportDto = toDto(reportEntity);

        List<HistoryDto> histories = reportEntity.getHistories().stream().map(historyEntity -> {
            HistoryDto history = new HistoryDto();
            history.setActionType(historyEntity.getActionType());
            history.setCreatedAt(historyEntity.getCreatedAt());
            history.setUser(userConverter.toDto(historyEntity.getUser()));
            return history;
        }).toList();
        reportDto.setHistories(histories);

        return reportDto;
    }

    public ReportEntity toEntity(ReportDto reportDto) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportId(reportDto.getReportId());
        reportEntity.setTitle(reportDto.getTitle());
        reportEntity.setDetail(reportDto.getDetail());
        reportEntity.setStatus(reportDto.getStatus());
        reportEntity.setCreatedAt(reportDto.getCreatedAt());
        reportEntity.setUpdatedAt(reportDto.getCreatedAt());

        UserEntity creator = userConverter.toEntity(reportDto.getCreator());
        reportEntity.setCreator(creator);

        return reportEntity;
    }
}
