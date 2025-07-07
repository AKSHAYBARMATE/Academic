package com.schoolerp.academic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDetail extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "class_name", nullable = false)
  private Integer className;

  @Column(name = "section_name", nullable = false, length = 50)
  private String sectionName;

  @Column(name = "class_code", nullable = false, length = 20)
  private String classCode;

  @Column(name = "max_student", nullable = false)
  private Integer maxStudent;

  @Column(name = "class_teacher", nullable = false)
  private Integer classTeacher;

  @Column(name = "status", nullable = false)
  private Boolean status = true;

  @Column(name = "record_status")
  private Boolean recordStatus;
}
