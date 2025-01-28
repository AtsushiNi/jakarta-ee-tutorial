package com.example.demoapp.infrastructure.repository;

import java.util.List;
import java.util.stream.Stream;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.entity.ReportEntity;
import com.example.demoapp.type.Status;

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

    public void apply(Integer id) {
        ReportEntity entity = em.find(ReportEntity.class, id);
        entity.setStatus(Status.C02_WAIT_REVIEW);

        em.merge(entity);
    }

    public void approve(Integer id) {
        ReportEntity entity = em.find(ReportEntity.class, id);
        entity.setStatus(Status.C03_COMPLETED);

        em.merge(entity);
    }

    public void remand(Integer id) {
        ReportEntity entity = em.find(ReportEntity.class, id);
        entity.setStatus(Status.C01_CREATING);

        em.merge(entity);
    }

    static private ReportDto convertToDto(ReportEntity entity) {
        ReportDto report = new ReportDto();
        report.setReportId(entity.getReportId());
        report.setTitle(entity.getTitle());
        report.setDetail(entity.getDetail());
        report.setStatus(entity.getStatus());
        report.setCreatedAt(entity.getCreatedAt());
        report.setUpdatedAt(entity.getUpdatedAt());

        return report;
    }

    static private ReportEntity convertToEntity(ReportDto dto) {
        ReportEntity report = new ReportEntity();
        report.setReportId(dto.getReportId());
        report.setTitle(dto.getTitle());
        report.setDetail(dto.getDetail());
        report.setStatus(dto.getStatus());
        report.setCreatedAt(dto.getCreatedAt());
        report.setUpdatedAt(dto.getCreatedAt());

        return report;
    }
}
