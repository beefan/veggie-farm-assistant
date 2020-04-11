package com.techelevator.model;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCHarvestDAO implements HarvestDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCHarvestDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Harvest> getAllHarvestsByBeds(int bedId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveHarvest(String cropName, int harvestId, int bedId, int cropId, int cropWeight, int cropCount) {
		String sql = "select id from bed where crop_name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
		result.next(); 
			int bedsId = result.getInt("id");
			sql = "update harvest set crop_weight = ?, crop_count = ? where bed_id = ?;";
			jdbcTemplate.update(sql, cropWeight, bedsId);
		}
		
		

	@Override
	public void saveHarvest(String cropName, int harvestId, int bedId, int cropId, int cropWeight) {
		String sql = "select id from bed where crop_name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
		result.next(); 
			int bedsId = result.getInt("id");
			sql = "update harvest set crop_weight = ?, where bed_id = ?";
			jdbcTemplate.update(sql, cropWeight, bedsId);
		
		
	}

	@Override
	public void updateHarvest(String cropName, int harvestId, int bedId, int cropId, int cropWeight, int cropCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHarvest(int harvestId, int bedId, int cropId, int cropWeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteHarvest(int harvestId) {
		// TODO Auto-generated method stub
		
	}

}
