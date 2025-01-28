package com.example.demoapp.infrastructure.repository;

import java.util.List;
import java.util.stream.Stream;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.infrastructure.converter.ReportConverter;
import com.example.demoapp.infrastructure.entity.ReportEntity;
import com.example.demoapp.type.Status;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class ReportRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ReportConverter reportConverter;

    public List<ReportDto> findAll() {
        Stream<ReportEntity> reportStream = em.createQuery("select r from ReportEntity r", ReportEntity.class).getResultStream();
        return reportStream.map(reportConverter::toDto).toList();
    }

    public ReportDto findById(Integer id) {
        ReportEntity report = em.find(ReportEntity.class, id);
        return reportConverter.toDto(report);
    }
    
    public void create(ReportDto report) {
        ReportEntity entity = reportConverter.toEntity(report);

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
}
