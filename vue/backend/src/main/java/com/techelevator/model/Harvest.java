package com.techelevator.model;

import java.time.LocalDate;

public class Harvest {
	
	private String cropName;

	private int harvestId;
	
	private int bedId;
	
	private int cropId;
	
	private double cropWeight;
	
	private int cropCount;
	
	private String userName;
	
	private LocalDate harvestDate;
	

	public LocalDate getHarvestDate() {
		return harvestDate;
	}

	public void setHarvestDate(LocalDate harvestDate) {
		this.harvestDate = harvestDate;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public int getHarvestId() {
		return harvestId;
	}

	public void setHarvestId(int harvestId) {
		this.harvestId = harvestId;
	}

	public int getBedId() {
		return bedId;
	}

	public void setBedId(int bedId) {
		this.bedId = bedId;
	}

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	public double getCropWeight() {
		return cropWeight;
	}

	public void setCropWeight(double cropWeight) {
		this.cropWeight = cropWeight;
	}

	public int getCropCount() {
		return cropCount;
	}

	public void setCropCount(int cropCount) {
		this.cropCount = cropCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
