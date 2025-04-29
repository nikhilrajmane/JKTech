package com.JKTech.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.PageDto;
import com.JKTech.demo.dto.UploadDto;

public interface UploadService {

	UploadDto uploadDocument(MultipartFile file, String author) throws Exception;

	List<UploadDto> searchDocuments(String query);

	PageDto filterDocuments(String author, String type, Date after, Date before, Pageable pageable);

}
