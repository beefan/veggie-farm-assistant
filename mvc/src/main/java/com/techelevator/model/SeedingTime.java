package com.techelevator.model;

public class SeedingTime {

	private int directSeedToHarvestInDays;
	
	private int directSeedToTransplantInDays;
	
	private int transplantToHarvestInDays;
	
	private String cropName;

	public int getDirectSeedToHarvestInDays() {
		return directSeedToHarvestInDays;
	}

	public void setDirectSeedToHarvestInDays(int directSeedToHarvestInDays) {
		this.directSeedToHarvestInDays = directSeedToHarvestInDays;
	}

	public int getDirectSeedToTransplantInDays() {
		return directSeedToTransplantInDays;
	}

	public void setDirectSeedToTransplantInDays(int directSeedToTransplantInDays) {
		this.directSeedToTransplantInDays = directSeedToTransplantInDays;
	}

	public int getTransplantToHarvestInDays() {
		return transplantToHarvestInDays;
	}

	public void setTransplantToHarvestInDays(int transplantToHarvestInDays) {
		this.transplantToHarvestInDays = transplantToHarvestInDays;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	
	
	
}
