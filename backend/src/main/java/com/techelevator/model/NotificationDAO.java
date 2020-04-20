package com.techelevator.model;

import java.util.List;

public interface NotificationDAO {
	
	public String compileNotification();
	
	public String getAllOfTodaysPlantings();
	
	public String getAllOfTodaysTransplants();
	
	public String getTodaysHarvests();
	
	public String getTomorrowsExpirations();

}
