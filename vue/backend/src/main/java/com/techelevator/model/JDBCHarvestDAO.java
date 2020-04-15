package com.techelevator.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCHarvestDAO implements HarvestDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCHarvestDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Harvest> getAllHarvestsByBeds(int bedId) {
		List<Harvest> harvests = new ArrayList<Harvest>();
		String sql = "select id, bed_id, crop_id, crop_weight, crop_count, username, harvest_date from harvest where bed_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bedId);
		
		while(results.next()) {
			Harvest harvest = new Harvest();
			harvest.setHarvestId(results.getInt("id"));
			harvest.setBedId(results.getInt("bed_id"));
			harvest.setCropWeight(results.getDouble("crop_weight"));
			harvest.setCropCount(results.getInt("crop_count"));
			harvest.setUserName(results.getString("username"));
			harvest.setHarvestDate(results.getDate("harvest_date").toLocalDate());
			harvests.add(harvest);
		}
		
		return harvests;
	}

	@Override
	public void saveHarvest(String cropName, int bedId, double cropWeight, int cropCount) {
		String sql = "Select id from crop where crop_name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cropName);
		results.next();
		int cropId = results.getInt("id");
		sql = "insert into harvest (crop_id, bed_id, crop_weight, crop_count, harvest_date, username) values(?,?,?,?,?,'user')";
		jdbcTemplate.update(sql, cropId, bedId, cropWeight, cropCount, LocalDate.now());
	}
		
	@Override
	public void saveHarvest(String cropName, int bedId, double cropWeight) {
		String sql = "Select id from crop where crop_name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cropName);
		results.next();
		int cropId = results.getInt("id");
		sql = "insert into harvest (crop_id, bed_id, crop_weight, harvest_date, username) values(?,?,?,?, 'user')";
		jdbcTemplate.update(sql, cropId, bedId, cropWeight, LocalDate.now());
		
	}
		

//	@Override
//	public void updateHarvest(String cropName, int harvestId, int bedId, int cropWeight, int cropCount) {
//		String sql = "select id from bed where crop_name = ?";
//		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
//		result.next(); 
//			int bedsId = result.getInt("id");
//			sql = "update harvest set crop_weight = ?, crop_count = ? where id = ?;";
//			jdbcTemplate.update(sql, cropWeight, bedsId);
//
//	}
//
//	@Override
//	public void updateHarvest(String cropName, int harvestId, int bedId, int cropWeight) {
//		String sql = "select id from bed where crop_name = ?";
//		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
//		result.next(); 
//			int bedsId = result.getInt("id");
//			sql = "update harvest set crop_weight = ?, where bed_id = ?";
//			jdbcTemplate.update(sql, cropWeight, bedsId);
//		
//	}

	@Override
	public void deleteHarvest(int harvestId) {
		String sql = "delete from harvest where id = ?";
		jdbcTemplate.update(sql, harvestId);
		
	}

	

}
