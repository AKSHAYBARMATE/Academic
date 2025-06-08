package com.schoolerp.academic.service;

import com.schoolerp.academic.request.ClassDetailRequestDto;
import com.schoolerp.academic.response.ClassDetailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassDetailService {
    ClassDetailResponseDto createClassDetail(ClassDetailRequestDto dto);
    ClassDetailResponseDto updateClassDetail(Long id, ClassDetailRequestDto dto);
    ClassDetailResponseDto getClassDetailById(Long id);
    Page<ClassDetailResponseDto> getAllClassDetails(Pageable pageable);
    void deleteClassDetail(Long id);
}