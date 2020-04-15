package com.techelevator.model;

public interface LossDAO {

	public void saveLossInfo(String cropName, double dollarAmount, double weightAmount, String lossType);
	
	
}
