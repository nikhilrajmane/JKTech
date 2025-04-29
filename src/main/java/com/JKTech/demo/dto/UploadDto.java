package com.JKTech.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UploadDto {

	private String id;
	private String filename;
	private String fileType;
	private long fileSize;
	private String content;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date uploadTime;
	private String author;

	public UploadDto() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
