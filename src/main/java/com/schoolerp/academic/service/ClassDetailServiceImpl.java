package com.schoolerp.academic.service;

import com.schoolerp.academic.entity.ClassDetail;
import com.schoolerp.academic.exception.CustomException;
import com.schoolerp.academic.repository.ClassDetailRepository;
import com.schoolerp.academic.request.ClassDetailRequestDto;
import com.schoolerp.academic.response.ClassDetailResponseDto;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassDetailServiceImpl implements ClassDetailService {

  private final ClassDetailRepository repository;
  private static final Logger logger = LoggerFactory.getLogger(ClassDetailServiceImpl.class);

  @Override
  public ClassDetailResponseDto createClassDetail(ClassDetailRequestDto dto) {
    logger.info("Creating new class detail: {}", dto);
    // Unique classCode validation
    if (repository.findByClassCodeAndRecordStatus(dto.getClassCode(), true) != null) {
      logger.warn("ClassDetail with classCode '{}' already exists.", dto.getClassCode());
      throw new CustomException(
          409, "ClassDetail with classCode '" + dto.getClassCode() + "' already exists.");
    }
    ClassDetail entity =
        ClassDetail.builder()
            .className(dto.getClassName())
            .sectionName(dto.getSectionName())
            .classCode(dto.getClassCode())
            .maxStudent(dto.getMaxStudent())
            .classTeacher(dto.getClassTeacher())
            .status(dto.getStatus() != null ? dto.getStatus() : true)
            .recordStatus(true)
            .build();
    ClassDetail saved = repository.save(entity);
    logger.info("Class detail created with ID: {}", saved.getId());
    return toResponseDto(saved);
  }

  @Override
  public ClassDetailResponseDto updateClassDetail(Long id, ClassDetailRequestDto dto) {
    logger.info("Updating class detail with ID: {} and data: {}", id, dto);
    ClassDetail classDetail = repository.findByIdAndRecordStatus(id, true);
    if (classDetail == null) {
      logger.warn("ClassDetail not found with ID: {}", id);
      throw new CustomException(404, "ClassDetail not found with ID: " + id);
    }
    // Unique classCode validation (ignore current record)
    ClassDetail existingWithCode =
        repository.findByClassCodeAndRecordStatus(dto.getClassCode(), true);
    if (existingWithCode != null && !existingWithCode.getId().equals(id)) {
      logger.warn("ClassDetail with classCode '{}' already exists.", dto.getClassCode());
      throw new CustomException(
          409, "ClassDetail with classCode '" + dto.getClassCode() + "' already exists.");
    }
    classDetail.setClassName(dto.getClassName());
    classDetail.setSectionName(dto.getSectionName());
    classDetail.setClassCode(dto.getClassCode());
    classDetail.setMaxStudent(dto.getMaxStudent());
    classDetail.setClassTeacher(dto.getClassTeacher());
    classDetail.setStatus(dto.getStatus() != null ? dto.getStatus() : classDetail.getStatus());
    ClassDetail updated = repository.save(classDetail);
    logger.info("Class detail updated: {}", updated);
    return toResponseDto(updated);
  }

  @Override
  public ClassDetailResponseDto getClassDetailById(Long id) {
    logger.info("Fetching class detail with ID: {}", id);
    ClassDetail entity = repository.findByIdAndRecordStatus(id, true);
    if (entity == null) {
      logger.warn("ClassDetail not found with ID: {}", id);
      throw new CustomException(404, "ClassDetail not found with ID: " + id);
    }
    return toResponseDto(entity);
  }

  @Override
  public Page<ClassDetailResponseDto> getAllClassDetails(Pageable pageable) {
    logger.info(
        "Fetching all class details with pagination: page={}, size={}",
        pageable.getPageNumber(),
        pageable.getPageSize());
    Page<ClassDetail> page = repository.findByRecordStatus(true, pageable);
    Page<ClassDetailResponseDto> dtoPage =
        new PageImpl<>(
            page.getContent().stream().map(this::toResponseDto).collect(Collectors.toList()),
            pageable,
            page.getTotalElements());
    logger.info("Fetched {} class details", dtoPage.getTotalElements());
    return dtoPage;
  }

  @Override
  public void deleteClassDetail(Long id) {
    logger.info("Attempting to soft delete class detail with ID: {}", id);
    ClassDetail entity = repository.findByIdAndRecordStatus(id, true);
    if (entity == null) {
      logger.warn("ClassDetail not found with ID: {}", id);
      throw new CustomException(404, "ClassDetail not found with ID: " + id);
    }
    entity.setRecordStatus(false);
    repository.save(entity);
    logger.info("Class detail soft deleted (recordStatus set to false) with ID: {}", id);
  }

  private ClassDetailResponseDto toResponseDto(ClassDetail entity) {
    return ClassDetailResponseDto.builder()
        .id(entity.getId())
        .className(entity.getClassName())
        .sectionName(entity.getSectionName())
        .classCode(entity.getClassCode())
        .maxStudent(entity.getMaxStudent())
        .classTeacher(entity.getClassTeacher())
        .status(entity.getStatus())
        .build();
  }
}
