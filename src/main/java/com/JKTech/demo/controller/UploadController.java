package com.JKTech.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.entity.UploadEntity;
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

	@GetMapping("/search")
	public ResponseEntity<List<UploadDto>> search(@RequestParam("query") String query) {
		List<UploadDto> results = uploadService.searchDocuments(query);
		return ResponseEntity.ok(results);
	}

	@GetMapping("/filter")
	public ResponseEntity<Page<UploadEntity>> filterDocuments(@RequestParam(required = false) String author,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date after,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date before,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "uploadTime,desc") String[] sort) {
		Sort sortObj = Sort.by(Sort.Order.desc("uploadTime"));
		if (sort.length == 2) {
			sortObj = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
		}

		Pageable pageable = PageRequest.of(page, size, sortObj);
		Page<UploadEntity> result = uploadService.filterDocuments(author, type, after, before, pageable);
		return ResponseEntity.ok(result);
	}

}
