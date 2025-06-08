package com.schoolerp.academic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Integer code=0;
    private List<FieldValidationError> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldValidationError {
        private String field;
        private String message;
    }
}

