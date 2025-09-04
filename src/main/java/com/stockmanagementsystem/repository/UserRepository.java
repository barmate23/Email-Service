package com.stockmanagementsystem.repository;

import com.stockmanagementsystem.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

//    Optional<Users> findByIsDeletedAndSubOrganizationIdAndId(boolean b, Integer subOrgId, int userId);


    Optional<Users> findByIsDeletedAndId(boolean b, int userId);
}
