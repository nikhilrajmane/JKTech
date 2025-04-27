package com.JKTech.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.service.UploadService;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@PostMapping("/upload")
	public UploadDto uploadDocument(@RequestParam("file") MultipartFile file) throws Exception {
		return uploadService.uploadDocument(file);
	}

}
