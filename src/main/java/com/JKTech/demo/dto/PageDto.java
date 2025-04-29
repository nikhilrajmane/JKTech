package com.JKTech.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDto {

	Integer pageSize;
	Integer pageNumber;
	Long totalElements;
	Integer totalPages;
	List<?> data;

}
