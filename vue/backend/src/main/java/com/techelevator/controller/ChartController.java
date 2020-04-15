package com.techelevator.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.ChartDAO;
import com.techelevator.model.Harvest;
import com.techelevator.model.Sale;

@RestController
@RequestMapping("/api/chart")
@CrossOrigin
public class ChartController {
	
	@Autowired
	private ChartDAO chartDao;
	
	@GetMapping("/sales")
	public Map<Integer, String> getCropsWithSalesInfo() {
		return chartDao.getCropsWithSalesData();
	}
	
	@GetMapping("/harvest")
	public Map<Integer, String> getCropsWithHarvestInfo() {
		return chartDao.getCropsWithHarvestData();
	}
	
	@GetMapping
	public Map<Integer, String> getCropsWithChartInfo() {
		return chartDao.getCropsWithChartData();
	}
	
	@GetMapping("/sales/{id}")
	public double[] getSalesById(@PathVariable int id) {
		return chartDao.sevenDaySalesByCrop(id);
	}
	
	@GetMapping("/harvest/{id}")
	public double[] getHarvestById(@PathVariable int id) {
		return chartDao.sevenDayHarvestByCrop(id);
	}
	
	@GetMapping("/loss/{id}")
	public double[] getLossById(@PathVariable int id) {
		return chartDao.sevenDayLossByCrop(id);
	}
	
	

}
