package com.techelevator.model;

import java.time.LocalDate;

public class Bed {
	
	private int bedId;

	private String fieldName;
	
	private String cropName;
	
	private LocalDate plantingDate;
	
	private LocalDate transplantDate;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public LocalDate getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(LocalDate plantingDate) {
		this.plantingDate = plantingDate;
	}

	public LocalDate getTransplantDate() {
		return transplantDate;
	}

	public void setTransplantDate(LocalDate transplantDate) {
		this.transplantDate = transplantDate;
	}

	public int getBedId() {
		return bedId;
	}

	public void setBedId(int bedId) {
		this.bedId = bedId;
	}
	
	
	
	
	
}
