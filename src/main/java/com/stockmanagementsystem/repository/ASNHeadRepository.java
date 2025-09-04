package com.stockmanagementsystem.repository;

import com.stockmanagementsystem.entity.ASNHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ASNHeadRepository extends JpaRepository<ASNHead,Integer> {
    ASNHead findByIsDeletedAndId(boolean b, Integer subType);}
