package com.techelevator.model;

import java.time.LocalDate;

import org.springframework.format.datetime.joda.LocalDateParser;

public class Bed {
	
	private int bedId;

	private int fieldId;
	
	private String cropName;
	
	private LocalDate plantingDate;
	
	private LocalDate transplantDate;

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
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
	
	public void setPlantingDate(String plantingDate) {
		this.plantingDate = LocalDate.parse(plantingDate);
	}

	public LocalDate getTransplantDate() {
		return transplantDate;
	}

	public void setTransplantDate(LocalDate transplantDate) {
		this.transplantDate = transplantDate;		
	}
	
	public void setTransplantDate(String transplantDate) {
		if (transplantDate != "") {
		this.transplantDate = LocalDate.parse(transplantDate);
		}else {
			this.transplantDate = null;
		}
	}

	public int getBedId() {
		return bedId;
	}

	public void setBedId(int bedId) {
		this.bedId = bedId;
	}
	
	
	
	
	
}
