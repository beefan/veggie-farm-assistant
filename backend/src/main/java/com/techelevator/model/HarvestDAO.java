package com.techelevator.model;

import java.util.List;

public interface HarvestDAO {

	public List<Harvest> getAllHarvestsByBeds(int bedId);
	
	public void saveHarvest(String cropName,int bedId, double cropWeight, int cropCount);
	
	public void saveHarvest( String cropName,int bedId, double cropWeight);
	
//	public void updateHarvest(String cropName, int harvestId, int bedId, int cropWeight, int cropCount);
//	
//	public void updateHarvest(String cropName, int harvestId, int bedId, int cropWeight);
	
	public void deleteHarvest(int harvestId);
	
	
}
