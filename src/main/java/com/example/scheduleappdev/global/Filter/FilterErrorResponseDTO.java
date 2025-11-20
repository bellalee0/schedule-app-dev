package com.example.scheduleappdev.global.Filter;

import com.example.scheduleappdev.global.Exception.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterErrorResponseDTO {
    private final int status;
    private final String code;
    private final String message;

    public FilterErrorResponseDTO(ErrorMessage errorCode, String message) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = message;
    }
}