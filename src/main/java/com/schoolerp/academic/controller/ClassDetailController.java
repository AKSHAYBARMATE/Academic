package com.schoolerp.academic.controller;

import com.schoolerp.academic.request.ClassDetailRequestDto;
import com.schoolerp.academic.response.ClassDetailResponseDto;
import com.schoolerp.academic.response.ApiResponse;
import com.schoolerp.academic.service.ClassDetailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/classDetails")
@RequiredArgsConstructor
public class ClassDetailController {

    private final ClassDetailService classDetailService;
    private static final Logger logger = LoggerFactory.getLogger(ClassDetailController.class);

    @PostMapping("/createClassDetail")
    public ResponseEntity<ApiResponse<ClassDetailResponseDto>> createClassDetail(@Valid @RequestBody ClassDetailRequestDto dto) {
        logger.info("Received request to create class detail: {}", dto);
        ClassDetailResponseDto saved = classDetailService.createClassDetail(dto);
        ApiResponse<ClassDetailResponseDto> response = ApiResponse.<ClassDetailResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Class detail created successfully")
                .data(saved)
                .code(1)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/classDetailById/{id}")
    public ResponseEntity<ApiResponse<ClassDetailResponseDto>> getClassDetail(@PathVariable Long id) {
        logger.info("Fetching class detail with ID: {}", id);
        ClassDetailResponseDto data = classDetailService.getClassDetailById(id);
        ApiResponse<ClassDetailResponseDto> response = ApiResponse.<ClassDetailResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Class detail fetched successfully")
                .data(data)
                .code(1)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateClassDetailById/{id}")
    public ResponseEntity<ApiResponse<ClassDetailResponseDto>> updateClassDetail(@PathVariable Long id, @Valid @RequestBody ClassDetailRequestDto dto) {
        logger.info("Updating class detail with ID: {} and data: {}", id, dto);
        ClassDetailResponseDto updated = classDetailService.updateClassDetail(id, dto);
        ApiResponse<ClassDetailResponseDto> response = ApiResponse.<ClassDetailResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Class detail updated successfully")
                .data(updated)
                .code(1)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllClassDetails")
    public ResponseEntity<ApiResponse<Page<ClassDetailResponseDto>>> getAllClassDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching all class details with page: {} and size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ClassDetailResponseDto> list = classDetailService.getAllClassDetails(pageable);
        ApiResponse<Page<ClassDetailResponseDto>> response = ApiResponse.<Page<ClassDetailResponseDto>>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Class details fetched successfully")
                .data(list)
                .code(1)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteClassDetailById{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClassDetail(@PathVariable Long id) {
        logger.info("Soft deleting class detail with ID: {}", id);
        classDetailService.deleteClassDetail(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Class detail deleted successfully")
                .data(null)
                .code(1)
                .build();
        return ResponseEntity.ok(response);
    }
}

