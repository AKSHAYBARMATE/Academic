package com.schoolerp.academic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDetailResponseDto {
    private Long id;
    private Integer className;
    private String sectionName;
    private String classCode;
    private Integer maxStudent;
    private Integer classTeacher;
    private Boolean status;
}

