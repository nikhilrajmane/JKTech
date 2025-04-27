package com.JKTech.demo.utility;

import java.io.InputStream;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtility {

	@SuppressWarnings("resource")
	public String extractFile(MultipartFile file) throws Exception {
		String filename = file.getOriginalFilename().toLowerCase();
		try (InputStream inputStream = file.getInputStream()) {
			if (filename.endsWith(".pdf")) {
				PDDocument document = PDDocument.load(inputStream);
				PDFTextStripper stripper = new PDFTextStripper();
				return stripper.getText(document);
			} else if (filename.endsWith(".docx")) {
				XWPFDocument document = new XWPFDocument(inputStream);
				XWPFWordExtractor extractor = new XWPFWordExtractor(document);
				return extractor.getText();
			} else if (filename.endsWith(".txt")) {
				Scanner scanner = new Scanner(inputStream, "UTF-8");
				return scanner.useDelimiter("\\A").next();
			} else {
				throw new IllegalArgumentException("Unsupported file type: " + filename);
			}
		}
	}

}
