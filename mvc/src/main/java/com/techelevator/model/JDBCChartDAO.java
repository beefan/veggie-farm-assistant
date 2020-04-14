package com.techelevator.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCChartDAO implements ChartDAO{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCChartDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Sale> sevenDaySalesByCrop(int cropId) {
		List<Sale> sales = new ArrayList<Sale>();
		String sql = "select crop_name, dollar_amount, sale_date, sale_type from sales " + 
				"join crop on crop.id = sales.crop_id " + 
				"where crop_id = ? and (sale_date > current_date - 7)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cropId);
		while(results.next()) {
			Sale sale = new Sale();
			sale.setCropName(results.getString("crop_name"));
			sale.setDollarAmount(results.getDouble("dollar_amount"));
			sale.setSaleDate(results.getDate("sale_date").toLocalDate());
			sale.setSaleType(results.getString("sale_type"));
			sales.add(sale);
		}
		return sales;
	}

	@Override
	public List<Harvest> sevenDayHarvestByCrop(int cropId) {
		List<Harvest> harvests = new ArrayList<Harvest>();
		String sql = "select crop_name, crop_weight, crop_count, harvest_date from harvest " + 
				"join crop on crop.id = harvest.crop_id " + 
				"where crop_id = ? and (harvest_date > current_date - 7);";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cropId);
		while(results.next()) {
			Harvest harvest = new Harvest();
			harvest.setCropName(results.getString("crop_name"));
			harvest.setCropWeight(results.getDouble("crop_weight"));
			harvest.setHarvestDate(results.getDate("harvest_date").toLocalDate());
			harvest.setCropCount(results.getInt("crop_count"));
			harvests.add(harvest);	
		}	
		return harvests;
	}

	@Override
	public Map<Integer, String> getCropsWithSalesData() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		String sql = "select crop_name, crop_id from sales " + 
				"join crop on crop.id = sales.crop_id " + 
				"where sale_date > current_date - 7;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			map.put(results.getInt("crop_id"), results.getString("crop_name"));
		}
		return map;
	}

	@Override
	public Map<Integer, String> getCropsWithHarvestData() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		String sql = "select crop_name, crop_id from harvest " + 
				"join crop on crop.id = harvest.crop_id " + 
				"where harvest_date > current_date - 7;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			map.put(results.getInt("crop_id"), results.getString("crop_name"));
		}
		return map;
	}

}
