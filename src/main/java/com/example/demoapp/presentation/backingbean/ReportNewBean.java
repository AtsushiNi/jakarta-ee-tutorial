package com.example.demoapp.presentation.backingbean;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.repository.ReportRepository;
import com.example.demoapp.security.CustomCallerPrincipal;
import com.example.demoapp.type.Status;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class ReportNewBean {

    @NotBlank(message = "タイトルは空白にできません")
    private String title;
    private String detail;

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private SecurityContext securityContext;

    public String create() {
        ReportDto newReport = new ReportDto();
        newReport.setTitle(title);
        newReport.setDetail(detail);
        newReport.setStatus(Status.C01_CREATING);
        UserDto loginUser = ((CustomCallerPrincipal) securityContext.getCallerPrincipal()).getUser();
        newReport.setCreator(loginUser);

        reportRepository.create(newReport);
        return "/reportList.xhtml?faces-redirect=true";
    }
}
