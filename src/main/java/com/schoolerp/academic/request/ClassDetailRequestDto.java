package com.schoolerp.academic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDetailRequestDto {
    @NotNull(message = "Class name is required")
    private Integer className;

    @NotBlank(message = "Section name is required")
    @Size(max = 50, message = "Section name must be at most 50 characters")
    private String sectionName;

    @NotBlank(message = "Class code is required")
    @Size(max = 20, message = "Class code must be at most 20 characters")
    private String classCode;

    @NotNull(message = "Max student is required")
    @Min(value = 1, message = "Max student must be at least 1")
    private Integer maxStudent;

    @NotNull(message = "Class teacher is required")
    private Integer classTeacher;

    private Boolean status;
}
