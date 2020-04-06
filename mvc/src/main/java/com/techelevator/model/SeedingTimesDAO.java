package com.techelevator.model;

import java.util.List;

public interface SeedingTimesDAO {
	
	public void save(String cropName, int directSeedToHarvestInDays);
	
	public void save(String cropName, int directSeedToTransplantInDays, int transplantToHarvestInDays);
		
	public List<SeedingTime> getAllSeedingTimes();

}
