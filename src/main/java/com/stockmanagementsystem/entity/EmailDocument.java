package com.stockmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_Email_Document")
public class EmailDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "email_generator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "data")
    @Lob
    private Byte[] data;

    @Column(name = "documentName")
    private String documentName;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private Email email;
}
