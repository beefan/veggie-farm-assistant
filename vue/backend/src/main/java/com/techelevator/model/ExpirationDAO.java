package com.techelevator.model;

import java.util.List;

public interface ExpirationDAO {

	public void save(String cropName, int daysToExpiration);
	
	public void delete(String cropName);
	
	public List<Expiration> getAllExpirationTimes();
	
	
}
