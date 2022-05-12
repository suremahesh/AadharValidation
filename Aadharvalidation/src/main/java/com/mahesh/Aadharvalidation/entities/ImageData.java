package com.mahesh.Aadharvalidation.entities;

public class ImageData {

	String status;
	String imagePath;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Override
	public String toString() {
		return "ImageData [status=" + status + ", imagePath=" + imagePath + "]";
	}
	
	
	
	
}
