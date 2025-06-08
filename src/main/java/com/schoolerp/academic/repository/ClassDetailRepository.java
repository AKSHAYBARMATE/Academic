package com.schoolerp.academic.repository;

import com.schoolerp.academic.entity.ClassDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Long> {


    ClassDetail findByIdAndRecordStatus(Long id, boolean b);

    Page<ClassDetail> findByRecordStatus(boolean b, org.springframework.data.domain.Pageable pageable);

    ClassDetail findByClassCodeAndRecordStatus(String classCode, boolean recordStatus);
    // Add custom query methods if needed
}

