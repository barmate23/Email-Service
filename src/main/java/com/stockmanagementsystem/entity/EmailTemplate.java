package com.stockmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_template")
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "emailTemplate_generator")
    @SequenceGenerator(name = "emailTemplate_generator", sequenceName = "EmailTemplate_SEQUENCE", initialValue=1,allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "tamplate_for")
    private String tamplateFor;

    @Column(name = "template_content", length = 10000)
    private String templateContent;

    @Column(name = "template_title")
    private String templateTitle;



}