package com.stockmanagementsystem.repository;

import com.stockmanagementsystem.entity.EmailDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDocumentRepository extends JpaRepository<EmailDocument,Integer> {
    EmailDocument findByEmailId(int emailId);
}
