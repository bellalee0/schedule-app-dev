package com.example.scheduleappdev.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 유저 수정 요청 DTO (유저명)
 */
@Getter
public class UpdateUsernameRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 10, message = "10자 이내의 이름을 입력해주세요.")
    private String username;
}
