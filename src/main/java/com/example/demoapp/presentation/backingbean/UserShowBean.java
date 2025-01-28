package com.example.demoapp.presentation.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.primefaces.event.FileUploadEvent;

import com.example.demoapp.dto.ReportDto;
import com.example.demoapp.dto.UserDto;
import com.example.demoapp.exception.NotFoundException;
import com.example.demoapp.infrastructure.repository.UserRepository;

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
public class UserShowBean implements Serializable {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] imageData;
    private List<ReportDto> reports;
    private ReportDto selectedReport;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ExternalContext externalContext;

    public void initUser() {
        UserDto user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("ID: " + userId + " のユーザーは存在しません");
        }

        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        reports = user.getReports();
        imageData = user.getImageData();
    }

    public void handleImageUpload(FileUploadEvent event) {
        byte[] imageData = event.getFile().getContent();
        setImageData(imageData);

        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setImageData(imageData);

        userRepository.update(userDto);
    }

    public void navigateToShowPage() throws IOException {
        String redirectUrl = externalContext.getRequestContextPath() + "/reportShow.xhtml?reportId=" + selectedReport.getReportId();
        externalContext.redirect(redirectUrl);
    }
}
