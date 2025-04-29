package com.JKTech.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.PageDto;
import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.entity.UploadEntity;
import com.JKTech.demo.repository.UploadRepository;
import com.JKTech.demo.service.UploadService;
import com.JKTech.demo.utility.FileUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadRepository uploadRepository;

	@Autowired
	private FileUtility fileUtility;

	@Override
	public UploadDto uploadDocument(MultipartFile file, String author) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		UploadDto uploadDto = new UploadDto();
		String content;
		try {
			content = fileUtility.extractFile(file);
			UploadEntity uploadEntity = new UploadEntity(file.getOriginalFilename(), file.getContentType(),
					file.getSize(), content, new Date(), author);

			uploadEntity = uploadRepository.save(uploadEntity);

			uploadDto = mapper.convertValue(uploadEntity, UploadDto.class);
		} catch (Exception e) {
			log.error("Error While Extracting File", e.getMessage());
			throw e;
		}
		return uploadDto;

	}

	@Override
	public List<UploadDto> searchDocuments(String keyword) {

		ObjectMapper mapper = new ObjectMapper();
		List<UploadEntity> uploadEntity = uploadRepository.searchByKeyword(keyword);
		List<UploadDto> listUploadDto = mapper.convertValue(uploadEntity, new TypeReference<List<UploadDto>>() {
		});

		return listUploadDto;
	}

	@Override
	public PageDto filterDocuments(String author, String type, Date after, Date before, Pageable pageable) {
		Specification<UploadEntity> spec = Specification.where(fileUtility.hasAuthor(author))
				.and(fileUtility.hasType(type)).and(fileUtility.uploadedAfter(after))
				.and(fileUtility.uploadedBefore(before));
		Page<UploadEntity> findAll = uploadRepository.findAll(spec, pageable);

		PageDto pageDto = new PageDto(findAll.getSize(), findAll.getNumber(), findAll.getTotalElements(),
				findAll.getTotalPages(), findAll.getContent());

		return pageDto;
	}

}
