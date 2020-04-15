package com.techelevator.model;

import java.time.LocalDate;

public class Loss {

	private String cropName;
	
	private double dollarAmount;
	
	private double weightAmount;
	
	private String lossType;
	
	private LocalDate lossDate;

	public LocalDate getLossDate() {
		return lossDate;
	}

	public void setLossDate(LocalDate lossDate) {
		this.lossDate = lossDate;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public double getDollarAmount() {
		return dollarAmount;
	}

	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
	}

	public double getWeightAmount() {
		return weightAmount;
	}

	public void setWeightAmount(double weightAmount) {
		this.weightAmount = weightAmount;
	}

	public String getLossType() {
		return lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}
	
	
	
	
}
