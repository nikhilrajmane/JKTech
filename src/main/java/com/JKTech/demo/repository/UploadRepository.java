package com.JKTech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JKTech.demo.entity.UploadEntity;

@Repository
public interface UploadRepository extends JpaRepository<UploadEntity, String> {

}
