package com.JKTech.demo.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.entity.UploadEntity;
import com.JKTech.demo.repository.UploadRepository;
import com.JKTech.demo.service.UploadService;
import com.JKTech.demo.utility.FileUtility;
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
	public UploadDto uploadDocument(MultipartFile file) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		UploadDto uploadDto = new UploadDto();
		String content;
		try {
			content = fileUtility.extractFile(file);
			UploadEntity uploadEntity = new UploadEntity(file.getOriginalFilename(), file.getContentType(),
					file.getSize(), content, new Date());

			uploadEntity = uploadRepository.save(uploadEntity);

			uploadDto = mapper.convertValue(uploadEntity, UploadDto.class);
		} catch (Exception e) {
			log.error("Error While Extracting File", e.getMessage());
			throw e;
		}
		return uploadDto;

	}

}
