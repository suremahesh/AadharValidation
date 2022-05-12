package com.mahesh.Aadharvalidation.controller;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;
import com.mahesh.Aadharvalidation.entities.AadharData;
import com.mahesh.Aadharvalidation.entities.AadharImageFileInfo;
import com.mahesh.Aadharvalidation.entities.ImageData;
import com.mahesh.Aadharvalidation.entities.ResponseBean;
import com.mahesh.Aadharvalidation.exception.BusinessException;
import com.mahesh.Aadharvalidation.service.FileUploadServiceInterface;
import com.mahesh.Aadharvalidation.utility.PDFExporter;

@RestController
@RequestMapping("/api")
public class FileUploadController {

	@Autowired
	private FileUploadServiceInterface fileUploadServiceInterface;

	@PostMapping(value = "/uploadAadharFile", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadAadharFile(@RequestParam("file") MultipartFile file) {

		try {

			AadharImageFileInfo respponse = fileUploadServiceInterface.saveAadharImage(file);

			if(!respponse.getAadharExtractedText().equalsIgnoreCase(""))
			{
			ResponseBean res = new ResponseBean();
			res.setId(respponse.getId());
			res.setStatus("success");
			return new ResponseEntity<Object>(res, HttpStatus.OK);
			}else {
				ResponseBean res = new ResponseBean();
				res.setId("");
				res.setStatus("success");
				res.setError("Unable to extract the Aadhar number from image");
				return new ResponseEntity<Object>(res, HttpStatus.OK);
			}
		} catch (BusinessException e) {
			ResponseBean res = new ResponseBean();
			res.setId("");
			res.setStatus(e.getErrorCode());
			res.setError(e.getMessage());
			return new ResponseEntity<Object>(res, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PostMapping("/uploadAadharData")
	public ResponseEntity<Object> uplpoadAadharData(@Validated @RequestBody AadharData aadharData) {

		try {
			String response = fileUploadServiceInterface.CheckAadharData(aadharData);
			if (response.equalsIgnoreCase("matched")) {
				ResponseBean res = new ResponseBean();
				res.setId(response);
				res.setStatus("success");

				return new ResponseEntity<Object>(res, HttpStatus.OK);
			} else {
				ResponseBean res = new ResponseBean();
				res.setId(response);
				res.setStatus("success");

				return new ResponseEntity<Object>(res, HttpStatus.OK);
			}
		} catch (BusinessException e) {
			ResponseBean res = new ResponseBean();
			res.setId("");
			res.setStatus(e.getErrorCode());
			res.setError(e.getMessage());
			return new ResponseEntity<Object>(res, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PostMapping("/uploadAadharDatagetpdf")
	public void uplpoadAadharDatareturnPdf(@Validated @RequestBody AadharData aadharData,
			HttpServletResponse response) {

		/*
		 * try { String response =
		 * fileUploadServiceInterface.CheckAadharData(aadharData); if
		 * (response.equalsIgnoreCase("matched")) { ResponseBean res = new
		 * ResponseBean(); res.setId(response); res.setStatus("success");
		 * 
		 * return new ResponseEntity<Object>(res, HttpStatus.OK);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * } else { ResponseBean res = new ResponseBean(); res.setId(response);
		 * res.setStatus("success");
		 * 
		 * return new ResponseEntity<Object>(res, HttpStatus.OK); } } catch
		 * (BusinessException e) { ResponseBean res = new ResponseBean(); res.setId("");
		 * res.setStatus(e.getErrorCode()); res.setError(e.getMessage()); return new
		 * ResponseEntity<Object>(res, HttpStatus.EXPECTATION_FAILED); }
		 */

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ImageData responsedata = fileUploadServiceInterface.CheckAadharDataReturnPdf(aadharData);
		System.out.println(responsedata);

		PDFExporter exporter = new PDFExporter(responsedata);
		try {
			exporter.export(response);
		} catch (DocumentException | IOException e) {

			e.printStackTrace();
		}

	}

}