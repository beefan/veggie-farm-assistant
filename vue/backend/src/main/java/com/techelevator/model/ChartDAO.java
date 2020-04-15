package com.techelevator.model;

import java.util.List;
import java.util.Map;

public interface ChartDAO {
	
	public double[] sevenDaySalesByCrop(int cropId);
	
	public double[] sevenDayHarvestByCrop(int cropId);
	
	public double[] sevenDayLossByCrop(int cropId);
	
	public Map<Integer, String> getCropsWithSalesData();
	
	public Map<Integer, String> getCropsWithHarvestData();

	public Map<Integer, String> getCropsWithChartData();
	
}
