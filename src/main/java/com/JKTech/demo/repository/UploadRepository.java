package com.JKTech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.JKTech.demo.entity.UploadEntity;

@Repository
public interface UploadRepository extends JpaRepository<UploadEntity, Long>, JpaSpecificationExecutor<UploadEntity> {

	@Query("SELECT d FROM UploadEntity d WHERE LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(d.filename) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<UploadEntity> searchByKeyword(String keyword);

}
