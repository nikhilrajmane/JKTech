package com.JKTech.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;

import com.JKTech.demo.dto.PageDto;
import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.entity.UploadEntity;
import com.JKTech.demo.repository.UploadRepository;
import com.JKTech.demo.utility.FileUtility;

public class UploadServiceImplTest {

	@InjectMocks
	private UploadServiceImpl uploadService;

	@Mock
	private UploadRepository uploadRepository;

	@Mock
	private FileUtility fileUtility;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testUploadDocument() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello".getBytes());
		String author = "John";

		when(fileUtility.extractFile(file)).thenReturn("hello content");

		UploadEntity savedEntity = new UploadEntity("test.txt", "text/plain", 5L, "hello content", new Date(), author);
		savedEntity.setId(1L);

		when(uploadRepository.save(any(UploadEntity.class))).thenReturn(savedEntity);

		UploadDto result = uploadService.uploadDocument(file, author);

		assertEquals("test.txt", result.getFilename());
		assertEquals("hello content", result.getContent());
	}

	@Test
	public void testSearchDocuments() {
		String keyword = "test";
		UploadEntity entity = new UploadEntity("test.txt", "text/plain", 5L, "test content", new Date(), "amit");
		List<UploadEntity> list = Arrays.asList(entity);

		when(uploadRepository.searchByKeyword(keyword)).thenReturn(list);

		List<UploadDto> result = uploadService.searchDocuments(keyword);

		assertEquals(1, result.size());
		assertEquals("test.txt", result.get(0).getFilename());
	}

	@Test
	public void testFilterDocuments() {
		Pageable pageable = PageRequest.of(0, 10);
		UploadEntity entity = new UploadEntity("Panel Expectations from Coding Exercise.pdf", "application/pdf", 100L,
				"pdf content", new Date(), "nik");
		Page<UploadEntity> page = new PageImpl<>(Arrays.asList(entity), pageable, 1);
		when(uploadRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
		PageDto result = uploadService.filterDocuments("Alice", "pdf", null, null, pageable);
		assertEquals(1, result.getTotalElements());
		assertEquals("Panel Expectations from Coding Exercise.pdf",
				((UploadEntity) result.getData().get(0)).getFilename());
	}
}
