package com.mahesh.Aadharvalidation.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.mahesh.Aadharvalidation.entities.AadharData;
import com.mahesh.Aadharvalidation.entities.AadharImageFileInfo;
import com.mahesh.Aadharvalidation.entities.ImageData;

public interface FileUploadServiceInterface {
	
	public AadharImageFileInfo saveAadharImage(MultipartFile file);
	public String CheckAadharData(AadharData aadharData);
	public ImageData CheckAadharDataReturnPdf(AadharData aadharData);

}
