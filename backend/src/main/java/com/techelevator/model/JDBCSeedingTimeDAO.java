package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCSeedingTimeDAO implements SeedingTimesDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCSeedingTimeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(String cropName, int directSeedToHarvestInDays) {
	int cropId;
		String sql = "select id from crop where crop_name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
		
		if (!result.next()) {
			sql = "insert into crop (crop_name) values (?) returning id";
			result = jdbcTemplate.queryForRowSet(sql, cropName);
			result.next();	
		}
		
		cropId = result.getInt("id");
		
		
		sql = "select id from seeding_times where crop_id = ?";
		result = jdbcTemplate.queryForRowSet(sql, cropId);
		
		if (!result.next()) {
			sql = "insert into seeding_times (crop_id, direct_seed_to_harvest) values (?, ?)";
			jdbcTemplate.update(sql, cropId, directSeedToHarvestInDays);
		}else {
			sql = "update seeding_times set direct_seed_to_harvest = ? where crop_id = ?";
			jdbcTemplate.update(sql, directSeedToHarvestInDays, cropId);
		} 
		
	}

	@Override
	public void save(String cropName, int directSeedToTransplantInDays, int transplantToHarvestInDays) {
		int cropId;
		String sql = "select id from crop where crop_name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
		
		if (!result.next()) {
			sql = "insert into crop (crop_name) values (?) returning id";
			result = jdbcTemplate.queryForRowSet(sql, cropName);
			result.next();	
		}
		
		cropId = result.getInt("id");
		
		
		sql = "select id from seeding_times where crop_id = ?";
		result = jdbcTemplate.queryForRowSet(sql, cropId);
		
		if (!result.next()) {
			sql = "insert into seeding_times (crop_id, direct_seed_to_transplant, transplant_to_harvest) values (?, ?, ?)";
			jdbcTemplate.update(sql, cropId, directSeedToTransplantInDays, transplantToHarvestInDays);
		}else {
			sql = "update seeding_times set direct_seed_to_transplant = ?, transplant_to_harvest = ? where crop_id = ?;";
			jdbcTemplate.update(sql, directSeedToTransplantInDays, transplantToHarvestInDays, cropId);
		} 
		
	}

	@Override
	public List<SeedingTime> getAllSeedingTimes() {
		List<SeedingTime> seedingTimes = new ArrayList<SeedingTime>();
		String sql = "select crop_name, direct_seed_to_harvest, direct_seed_to_transplant, transplant_to_harvest FROM seeding_times JOIN crop ON seeding_times.crop_id = crop.id order by crop_name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		
		while(results.next()) {
			SeedingTime st = new SeedingTime();
			st.setCropName(results.getString("crop_name"));
			//in case any of the three fields are null
			try {
				st.setDirectSeedToHarvestInDays(results.getInt("direct_seed_to_harvest"));
			}
			catch (InvalidResultSetAccessException e){
				//don't set
			}
			try {
				st.setDirectSeedToTransplantInDays(results.getInt("direct_seed_to_transplant"));
			}
			catch (InvalidResultSetAccessException e){
				//don't set
			}
			try {
				st.setTransplantToHarvestInDays(results.getInt("transplant_to_harvest"));
			}
			catch (InvalidResultSetAccessException e){
				//don't set
			}
			seedingTimes.add(st);
		}
		return seedingTimes;
	}

	@Override
	public void delete(String cropName) {

		String sql = "delete from seeding_times where crop_id in (select id from crop where crop_name = ?);";
		jdbcTemplate.update(sql, cropName);
		
		sql= "delete from crop where crop_name= ?";
		jdbcTemplate.update(sql, cropName);
		
	}

}
