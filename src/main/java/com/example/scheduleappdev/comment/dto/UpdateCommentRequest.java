package com.example.scheduleappdev.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 댓글 수정 요청 DTO (댓글)
 */
@Getter
public class UpdateCommentRequest {
    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(max = 100, message = "100자 이내의 댓글을 입력해주세요.")
    private String comment;
}
