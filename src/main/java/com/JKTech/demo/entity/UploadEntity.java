package com.JKTech.demo.entity;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadEntity {
	
	@Id
	private String id;
    private String title;
    private String content;

}
