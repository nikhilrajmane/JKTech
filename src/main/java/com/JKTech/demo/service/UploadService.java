package com.JKTech.demo.service;

import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.UploadDto;

public interface UploadService {

	UploadDto uploadDocument(MultipartFile file) throws Exception;

}
