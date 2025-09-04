package com.stockmanagementsystem.repository;

import com.stockmanagementsystem.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailsRepository extends JpaRepository<Email,Integer> {
    List<Email> findByAttemptCountLessThanEqual(int i);
}
