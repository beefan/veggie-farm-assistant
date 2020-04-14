package com.techelevator.model;

import java.util.List;
import java.util.Map;

public interface ChartDAO {
	
	public List<Double> sevenDaySalesByCrop(int cropId);
	
	public List<Double> sevenDayHarvestByCrop(int cropId);
	
	public Map<Integer, String> getCropsWithSalesData();
	
	public Map<Integer, String> getCropsWithHarvestData();
	
}
