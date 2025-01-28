package com.example.demoapp.infrastructure.repository;

import java.util.List;
import java.util.stream.Stream;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.entity.ReportEntity;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class ReportRepository {

    @PersistenceContext
    private EntityManager em;

    public List<ReportDto> findAll() {
        Stream<ReportEntity> reportStream = em.createQuery("select r from ReportEntity r", ReportEntity.class).getResultStream();
        return reportStream.map(ReportRepository::convertToDto).toList();
    }

    public ReportDto findById(Integer id) {
        ReportEntity report = em.find(ReportEntity.class, id);
        return convertToDto(report);
    }
    
    public void create(ReportDto report) {
        ReportEntity entity = convertToEntity(report);

        em.persist(entity);
    }

    static private ReportDto convertToDto(ReportEntity entity) {
        ReportDto report = new ReportDto();
        report.setReportId(entity.getReportId());
        report.setTitle(entity.getTitle());
        report.setDetail(entity.getDetail());

        return report;
    }

    static private ReportEntity convertToEntity(ReportDto dto) {
        ReportEntity report = new ReportEntity();
        report.setReportId(dto.getReportId());
        report.setTitle(dto.getTitle());
        report.setDetail(dto.getDetail());

        return report;
    }
}
