package com.JKTech.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.entity.UploadEntity;

public interface UploadService {

	UploadDto uploadDocument(MultipartFile file) throws Exception;

	List<UploadDto> searchDocuments(String query);

	Page<UploadEntity> filterDocuments(String author, String type, Date after, Date before,
			Pageable pageable);

}
