package com.JKTech.demo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.JKTech.demo.constant.AppConstants;
import com.JKTech.demo.constant.ErrorConstants;
import com.JKTech.demo.dto.PageDto;
import com.JKTech.demo.dto.UploadDto;
import com.JKTech.demo.entity.UploadEntity;
import com.JKTech.demo.exception.UploadException;
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
	public UploadDto uploadDocument(MultipartFile file, String author) throws Exception, UploadException {

		ObjectMapper mapper = new ObjectMapper();
		UploadDto uploadDto = new UploadDto();
		String content;
		if (null != file && null != author) {
			content = fileUtility.extractFile(file);
			UploadEntity uploadEntity = new UploadEntity(file.getOriginalFilename(), file.getContentType(),
					file.getSize(), content, new Date(), author);
			uploadEntity = uploadRepository.save(uploadEntity);
			uploadDto = mapper.convertValue(uploadEntity, UploadDto.class);
		} else {
			throw new UploadException(ErrorConstants.INVALID_DETAILS);
		}

		return uploadDto;

	}

	@Override
	public List<UploadDto> searchDocuments(String keyword) throws UploadException {

		ObjectMapper mapper = new ObjectMapper();
		List<UploadDto> listUploadDto = new ArrayList<>();

		if (null != keyword) {
			List<UploadEntity> uploadEntity = uploadRepository.searchByKeyword(keyword);
			listUploadDto = mapper.convertValue(uploadEntity, new TypeReference<List<UploadDto>>() {
			});
		} else {
			throw new UploadException(ErrorConstants.MANDATORY_KEYWORD);
		}

		return listUploadDto;
	}

	@Override
	public PageDto filterDocuments(String author, String type, String after, String before, Pageable pageable)
			throws ParseException, UploadException {

		SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);
		Date afterDate = null;
		Date beforeDate = null;
		try {
			if (null != after && null != before) {
				afterDate = formatter.parse(after);
				beforeDate = formatter.parse(before);
			}

			if (null != afterDate && null != beforeDate) {
				Specification<UploadEntity> spec = Specification.where(fileUtility.hasAuthor(author))
						.and(fileUtility.hasType(type)).and(fileUtility.uploadedAfter(afterDate))
						.and(fileUtility.uploadedBefore(beforeDate));
				Page<UploadEntity> findAll = uploadRepository.findAll(spec, pageable);

				PageDto pageDto = new PageDto(findAll.getSize(), findAll.getNumber(), findAll.getTotalElements(),
						findAll.getTotalPages(), findAll.getContent());

				return pageDto;

			} else {
				throw new UploadException(ErrorConstants.DATE_CANNOT_NULL);
			}

		} catch (ParseException e) {
			log.error("Error While parsing Date", e.getMessage());
			throw e;
		}

	}

}
