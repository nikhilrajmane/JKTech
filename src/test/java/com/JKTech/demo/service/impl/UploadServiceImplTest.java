package com.JKTech.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.constant.ErrorConstants;
import com.JKTech.demo.dto.PageDto;
import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.entity.UploadEntity;
import com.JKTech.demo.exception.UploadException;
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
	void testUploadDocument_Success() throws Exception {
		MultipartFile mockFile = mock(MultipartFile.class);
		when(mockFile.getOriginalFilename()).thenReturn("test.pdf");
		when(mockFile.getContentType()).thenReturn("application/pdf");
		when(mockFile.getSize()).thenReturn(1234L);

		when(fileUtility.extractFile(mockFile)).thenReturn("File content");

		UploadEntity savedEntity = new UploadEntity("test.pdf", "application/pdf", 1234L, "File content", new Date(),
				"amit");
		when(uploadRepository.save(any(UploadEntity.class))).thenReturn(savedEntity);

		UploadDto result = uploadService.uploadDocument(mockFile, "Alice");

		assertEquals("test.pdf", result.getFilename());
	}

	@Test
	void testUploadDocument_NullFile_ThrowsUploadException() {
		UploadException ex = assertThrows(UploadException.class, () -> {
			uploadService.uploadDocument(null, "amit");
		});

		assertEquals(ErrorConstants.INVALID_DETAILS, ex.getMessage());
	}

	@Test
	void testSearchDocuments_ValidKeyword_ReturnsList() throws UploadException {
		String keyword = "test";
		UploadEntity entity = new UploadEntity();
		entity.setFilename("doc.txt");

		when(uploadRepository.searchByKeyword(keyword)).thenReturn(Collections.singletonList(entity));

		List<UploadDto> result = uploadService.searchDocuments(keyword);

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void testSearchDocuments_NullKeyword_ThrowsException() {
		UploadException exception = assertThrows(UploadException.class, () -> {
			uploadService.searchDocuments(null);
		});

		assertEquals(ErrorConstants.MANDATORY_KEYWORD, exception.getMessage());
	}

	@Test
	void testUploadDocument_NullAuthor_ThrowsUploadException() {
		MultipartFile file = mock(MultipartFile.class);

		UploadException ex = assertThrows(UploadException.class, () -> {
			uploadService.uploadDocument(file, null);
		});

		assertEquals(ErrorConstants.INVALID_DETAILS, ex.getMessage());
	}

	@Test
	void testUploadDocument_ExtractFileThrowsException() throws Exception {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn("error.pdf");
		when(file.getContentType()).thenReturn("application/pdf");
		when(file.getSize()).thenReturn(500L);

		when(fileUtility.extractFile(any())).thenThrow(new IOException("Read error"));

		assertThrows(IOException.class, () -> uploadService.uploadDocument(file, "amit"));
	}

	@Test
	void testFilterDocuments_ValidInputs_ReturnsPageDto() throws Exception {
		String author = "amit", type = "pdf";
		String after = "2025-04-28 23:29:41.356", before = "2025-04-30 23:29:41.356";
		Pageable pageable = PageRequest.of(0, 10);
		UploadEntity entity = new UploadEntity();

		when(fileUtility.hasAuthor(author)).thenReturn((Specification<UploadEntity>) (root, query, cb) -> null);
		when(fileUtility.hasType(type)).thenReturn((Specification<UploadEntity>) (root, query, cb) -> null);
		when(fileUtility.uploadedAfter(any())).thenReturn((Specification<UploadEntity>) (root, query, cb) -> null);
		when(fileUtility.uploadedBefore(any())).thenReturn((Specification<UploadEntity>) (root, query, cb) -> null);

		Page<UploadEntity> mockPage = new PageImpl<>(Arrays.asList(entity), pageable, 1);
		when(uploadRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockPage);

		PageDto result = uploadService.filterDocuments(author, type, after, before, pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
	}

	@Test
	void testFilterDocuments_NullDates_ThrowsUploadException() {
		UploadException ex = assertThrows(UploadException.class,
				() -> uploadService.filterDocuments("amit", "pdf", null, null, PageRequest.of(0, 10)));

		assertEquals(ErrorConstants.DATE_CANNOT_NULL, ex.getMessage());
	}

	@Test
	void testFilterDocuments_InvalidDateFormat_ThrowsParseException() {
		assertThrows(ParseException.class,
				() -> uploadService.filterDocuments("amit", "pdf", "bad-date", "2025-04-28", PageRequest.of(0, 10)));
	}
}
