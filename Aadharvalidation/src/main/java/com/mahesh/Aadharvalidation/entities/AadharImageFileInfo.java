package com.mahesh.Aadharvalidation.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "aadhar_imagefile_info")
public class AadharImageFileInfo {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@Column(name = "extracted_text", nullable = false)
	private String extractedText;
	
	@Column(name = "aadhar_extracted_text", nullable = false)
	private String aadharExtractedText;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtractedText() {
		return extractedText;
	}

	public void setExtractedText(String extractedText) {
		this.extractedText = extractedText;
	}

	public String getAadharExtractedText() {
		return aadharExtractedText;
	}

	public void setAadharExtractedText(String aadharExtractedText) {
		this.aadharExtractedText = aadharExtractedText;
	}

	@Override
	public String toString() {
		return "AadharImageFileInfo [id=" + id + ", fileName=" + fileName + ", extractedText=" + extractedText
				+ ", aadharExtractedText=" + aadharExtractedText + "]";
	}
	
	
	
	
	

}
