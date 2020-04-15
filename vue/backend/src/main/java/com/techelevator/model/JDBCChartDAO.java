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
	public double[] sevenDayLossByCrop(int cropId) {
		List<Loss> losses = new ArrayList<Loss>();
		String sql = "select crop_name, weight_info, loss_type, dollar_amount, loss_date " +
					"from loss join crop on crop.id = loss.crop_id " + 
					"where crop_id = ? and (loss_date > current_date - 7)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cropId);
		while(results.next()) {
			Loss loss = new Loss();
			loss.setCropName(results.getString("crop_name"));
			loss.setDollarAmount(results.getDouble("dollar_amount"));
			loss.setLossType(results.getString("loss_type"));
			loss.setWeightAmount(results.getDouble("weight_info"));
			loss.setLossDate(results.getDate("loss_date").toLocalDate());
			losses.add(loss);
			}
		
		double[] result = new double[7];
		for(int i = 6; i >= 0; i--) {
			LocalDate day = LocalDate.now().minusDays(i);
			double counter = 0.0;
			for (Loss current : losses) {
				if (current.getLossDate().compareTo(day) == 0) {
					counter += current.getDollarAmount();
				}
			}
			result[6-i] = counter;
		}
		
		
		return result;
	}
	
	@Override
	public double[] sevenDaySalesByCrop(int cropId) {
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
		double[] result = new double[7];
		for(int i = 6; i >= 0; i--) {
			LocalDate day = LocalDate.now().minusDays(i);
			double counter = 0.0;
			for (Sale current : sales) {
				if (current.getSaleDate().compareTo(day) == 0) {
					counter += current.getDollarAmount();
				}
			}
			result[6-i] = counter;
		}
		return result;
	}

	@Override
	public double[] sevenDayHarvestByCrop(int cropId) {
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
		double[] result = new double[7];
		for(int i = 6; i >= 0; i--) {
			LocalDate day = LocalDate.now().minusDays(i);
			double counter = 0.0;
			for (Harvest current : harvests) {
				if (current.getHarvestDate().compareTo(day) == 0) {
					counter += current.getCropWeight();
				}
			}
			result[6-i] = counter;
		}
		return result;
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

	@Override
	public Map<Integer, String> getCropsWithChartData() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		String sql = "select crop_name, crop_id from loss " + 
					"join crop on crop.id = loss.crop_id " +
					"where loss_date > loss_date - 7";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			map.put(results.getInt("crop_id"), results.getString("crop_name"));
		}

		sql = "select crop_name, crop_id from harvest " + 
				"join crop on crop.id = harvest.crop_id " + 
				"where harvest_date > current_date - 7;";
		results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			map.put(results.getInt("crop_id"), results.getString("crop_name"));
		}
		
		sql = "select crop_name, crop_id from sales " + 
				"join crop on crop.id = sales.crop_id " + 
				"where sale_date > current_date - 7;";
		results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			map.put(results.getInt("crop_id"), results.getString("crop_name"));
		}
		
		return map;
	}

}
