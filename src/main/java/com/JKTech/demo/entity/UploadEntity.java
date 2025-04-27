package com.JKTech.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uploads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String filename;

	private String fileType;

	private long fileSize;

	@Lob
	@Column(length = 50000)
	private String content; // Extracted text

	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date uploadTime;

	public UploadEntity(String filename, String fileType, long fileSize, String content, Date uploadTime) {
		super();
		this.filename = filename;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.content = content;
		this.uploadTime = uploadTime;
	}

}
