package com.JKTech.demo.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.PageDto;
import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.exception.UploadException;

public interface UploadService {

	UploadDto uploadDocument(MultipartFile file, String author) throws Exception, UploadException;

	List<UploadDto> searchDocuments(String query) throws UploadException;

	PageDto filterDocuments(String author, String type, String after, String before, Pageable pageable)
			throws ParseException, UploadException;

}
