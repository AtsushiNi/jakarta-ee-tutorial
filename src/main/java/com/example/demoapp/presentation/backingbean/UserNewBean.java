package com.example.demoapp.presentation.backingbean;

import com.example.demoapp.dto.UserDto;
import com.example.demoapp.infrastructure.repository.UserRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class UserNewBean {

    @NotBlank(message = "姓は空白にできません")
    private String firstName;

    @NotBlank(message = "名は空白にできません")
    private String lastName;

    @NotBlank(message = "メールアドレスは空白にできません")
    @Email(message = "有効なメールアドレスを入力してください")
    private String email;

    @NotBlank(message = "パスワードは空白にできません")
    @Size(min = 6, max = 12, message = "6文字以上12文字以下にしてください")
    private String password;

    @Inject
    private UserRepository userRepository;

    public String create() {
        UserDto user = new UserDto();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.create(user);
        return "userList?faces-redirect=true";
    }
}
