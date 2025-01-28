package com.example.demoapp.infrastructure.repository;

import java.util.List;
import java.util.stream.Stream;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.converter.ReportConverter;
import com.example.demoapp.infrastructure.converter.UserConverter;
import com.example.demoapp.infrastructure.entity.HistoryEntity;
import com.example.demoapp.infrastructure.entity.ReportEntity;
import com.example.demoapp.infrastructure.entity.UserEntity;
import com.example.demoapp.security.CustomCallerPrincipal;
import com.example.demoapp.type.ActionType;
import com.example.demoapp.type.Status;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class ReportRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ReportConverter reportConverter;

    @Inject
    private UserConverter userConverter;

    public List<ReportDto> findAll() {
        Stream<ReportEntity> reportStream = em.createQuery("select r from ReportEntity r", ReportEntity.class).getResultStream();
        return reportStream.map(reportConverter::toDto).toList();
    }

    public ReportDto findById(Integer id) {
        ReportEntity report = em.find(ReportEntity.class, id);
        return reportConverter.toDtoWithHistories(report);
    }
    
    public void create(ReportDto report) {
        ReportEntity entity = reportConverter.toEntity(report);

        addNewHistory(entity, ActionType.C01_CREATE);

        em.persist(entity);
    }

    public void apply(Integer id) {
        ReportEntity entity = em.find(ReportEntity.class, id);

        entity.setStatus(Status.C02_WAIT_REVIEW);
        addNewHistory(entity, ActionType.C02_APPLY);

        em.merge(entity);
    }

    public void approve(Integer id) {
        ReportEntity entity = em.find(ReportEntity.class, id);

        entity.setStatus(Status.C03_COMPLETED);
        addNewHistory(entity, ActionType.C03_APPROVE);

        em.merge(entity);
    }

    public void remand(Integer id) {
        ReportEntity entity = em.find(ReportEntity.class, id);

        entity.setStatus(Status.C01_CREATING);
        addNewHistory(entity, ActionType.C04_REMAND);

        em.merge(entity);
    }

    private void addNewHistory(ReportEntity report, ActionType actionType) {
        HistoryEntity history = new HistoryEntity();
        history.setReport(report);
        history.setActionType(actionType);

        UserDto loginUserDto = ((CustomCallerPrincipal)securityContext.getCallerPrincipal()).getUser();
        UserEntity loginUserEntity = userConverter.toEntity(loginUserDto);
        history.setUser(loginUserEntity);

        report.getHistories().add(history);
    }
}
