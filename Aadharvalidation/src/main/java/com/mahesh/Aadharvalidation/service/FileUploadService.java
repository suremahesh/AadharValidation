package com.mahesh.Aadharvalidation.service;

import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mahesh.Aadharvalidation.entities.AadharData;
import com.mahesh.Aadharvalidation.entities.AadharImageFileInfo;
import com.mahesh.Aadharvalidation.entities.ImageData;
import com.mahesh.Aadharvalidation.entities.ResponseBean;
import com.mahesh.Aadharvalidation.repository.DataRepository;

import net.sourceforge.tess4j.*;

@Service
public class FileUploadService implements FileUploadServiceInterface {

	@Autowired
	DataRepository repo;
	static String path="C:\\Users\\003J20744\\IMAGES\\";

	@Override
	public AadharImageFileInfo saveAadharImage(MultipartFile file) {

		try {
			
			//for making the file name unique get the current ms
			
			  Calendar date = Calendar.getInstance();
			  long millisecondsDate = date.getTimeInMillis();
			 
			

			String ext=getFileExtension(file.getOriginalFilename());
		
			
			//saving to storage
			File f = new File(path + String.valueOf(millisecondsDate)+ext);
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(file.getBytes());
			fo.close();


			//extracting text from image
			ITesseract instance = new Tesseract();
			String result = instance.doOCR(f);
//			System.out.println(result);
			
			
			//code for getting aadhar number from text
			String strPattern="\\d{4}\\s\\d{4}\\s\\d{4}";
			  Pattern pattern = Pattern.compile(strPattern);
			  
			  Matcher matcher = pattern.matcher(result);
			  String val = "";
			  
			  if (matcher.find()) {
				  System.out.println(matcher.group(0));
				  val=matcher.group(0);
			  }
			  
			  
			  // val = matcher.group(1)+matcher.group(2)+matcher.group(3);
//			  System.out.println("aadhar number" + val);
			 
			
			//saving to databse table
			AadharImageFileInfo info=new AadharImageFileInfo();
			
			
			info.setAadharExtractedText(val);
			
			info.setFileName(path + String.valueOf(millisecondsDate)+ext);

			info.setExtractedText(result);
			AadharImageFileInfo response=repo.save(info);
//			System.out.println(response.getId());
			
			
			return info;

		} catch (Exception e) {
			throw new com.mahesh.Aadharvalidation.exception.BusinessException("603",
					"Something went wrong in Service layer" + e.getMessage());
		}

	}

	@Override
	public ImageData CheckAadharDataReturnPdf(AadharData aadharData) {
		
//		 aadharImageFileInfo=new AadharImageFileInfo();
		try {
			
			AadharImageFileInfo aadharImageFileInfo = repo.findById(aadharData.getId()).get();
//			System.out.println(aadharImageFileInfo);
		if(aadharData.getAadharNumber().equalsIgnoreCase(aadharImageFileInfo.getAadharExtractedText()))
		{
			
			ImageData resp=new ImageData();
//			return aadharImageFileInfo;
//			resp="matched";
			resp.setStatus("matched");
			resp.setImagePath(aadharImageFileInfo.getFileName());
			return resp;
			
		}else {
			ImageData resp=new ImageData();
			resp.setStatus("not matched");
			resp.setImagePath(aadharImageFileInfo.getFileName());
			return resp;
		}
		
		}catch (IllegalArgumentException e) {
			throw new com.mahesh.Aadharvalidation.exception.BusinessException("606","given  id is null, please send some id to be searched" + e.getMessage());
		}
		catch (java.util.NoSuchElementException e) {
			throw new com.mahesh.Aadharvalidation.exception.BusinessException("607","Record is not available" + e.getMessage());
		}catch (Exception e) {
			throw new com.mahesh.Aadharvalidation.exception.BusinessException("609","Something went wrong in Service layer while fetching data" + e.getMessage());
		}
//		return resp;

	}
	
	private String getFileExtension(String  name) {
	    
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}

	@Override
	public String CheckAadharData(AadharData aadharData) {
		String resp="";
//		 aadharImageFileInfo=new AadharImageFileInfo();
		try {
			
			AadharImageFileInfo aadharImageFileInfo = repo.findById(aadharData.getId()).get();
			
		if(aadharData.getAadharNumber().equalsIgnoreCase(aadharImageFileInfo.getAadharExtractedText()))
		{
//			return aadharImageFileInfo;
			resp="matched";
			
			
		}else {
			resp="not matched";
		}
		
		}catch (IllegalArgumentException e) {
			throw new com.mahesh.Aadharvalidation.exception.BusinessException("606","given  id is null, please send some id to be searched" + e.getMessage());
		}
		catch (java.util.NoSuchElementException e) {
			throw new com.mahesh.Aadharvalidation.exception.BusinessException("607","Record is not available" + e.getMessage());
		}catch (Exception e) {
			throw new com.mahesh.Aadharvalidation.exception.BusinessException("609","Something went wrong in Service layer while fetching data" + e.getMessage());
		}
		return resp;
	}
	

}