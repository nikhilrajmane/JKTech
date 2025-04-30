package com.JKTech.demo.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.PageDto;
import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.exception.UploadException;
import com.JKTech.demo.service.UploadService;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@PostMapping("/upload")
	public UploadDto uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("author") String author)
			throws Exception {
		return uploadService.uploadDocument(file, author);
	}

	@GetMapping("/search")
	public ResponseEntity<List<UploadDto>> search(@RequestParam("query") String query) throws UploadException {
		List<UploadDto> results = uploadService.searchDocuments(query);
		return ResponseEntity.ok(results);
	}

	@GetMapping("/filter")
	public ResponseEntity<PageDto> filterDocuments(@RequestParam(required = false) String author,
			@RequestParam(required = false) String type, @RequestParam(required = false) String after,
			@RequestParam(required = false) String before, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "uploadTime") String sort)
			throws ParseException, UploadException {
		Sort sortObj = Sort.by(Sort.Order.desc(sort));
		Pageable pageable = PageRequest.of(page, size, sortObj);

		PageDto result = uploadService.filterDocuments(author, type, after, before, pageable);
		return ResponseEntity.ok(result);
	}

}
