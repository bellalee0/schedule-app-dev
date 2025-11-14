package com.example.scheduleappdev.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 유저 생성 요청 DTO (유저명, 이메일, 비밀번호)
 */
@Getter
public class CreateUserRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 10, message = "10자 이내의 이름을 입력해주세요.")
    private String username;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "'id@domain.dom'의 형식으로 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
