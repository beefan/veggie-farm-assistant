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
		String sql = "select id from crop where crop_name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
		if (result.next()) {
			int cropId = result.getInt("id");
			sql = "update seeding_times set direct_seed_to_harvest = ? where crop_id = ?";
			jdbcTemplate.update(sql, directSeedToHarvestInDays, cropId);
		} else {
			sql = "insert into crop (crop_name) values (?) returning id";
			SqlRowSet result2 = jdbcTemplate.queryForRowSet(sql, cropName);
			result2.next();
			int cropId = result2.getInt("id");
			sql = "insert into seeding_times (crop_id, direct_seed_to_harvest) values (?, ?)";
			jdbcTemplate.update(sql, cropId, directSeedToHarvestInDays);
		}
		
	}

	@Override
	public void save(String cropName, int directSeedToTransplantInDays, int transplantToHarvestInDays) {
		String sql = "select id from crop where crop_name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
		if (result.next()) {
			int cropId = result.getInt("id");
			sql = "update seeding_times set direct_seed_to_transplant = ?, transplant_to_harvest = ? where crop_id = ?;";
			jdbcTemplate.update(sql, directSeedToTransplantInDays, transplantToHarvestInDays, cropId);
		} else {
			sql = "insert into crop (crop_name) values (?) returning id";
			SqlRowSet result2 = jdbcTemplate.queryForRowSet(sql, cropName);
			result2.next();
			int cropId = result2.getInt("id");
			sql = "insert into seeding_times (crop_id, direct_seed_to_transplant, transplant_to_harvest) values (?, ?, ?)";
			jdbcTemplate.update(sql, cropId, directSeedToTransplantInDays, transplantToHarvestInDays);
		}
		
	}

	@Override
	public List<SeedingTime> getAllSeedingTimes() {
		List<SeedingTime> seedingTimes = new ArrayList<SeedingTime>();
		String sql = "select crop_name, direct_seed_to_harvest, direct_seed_to_transplant, transplant_to_harvest FROM seeding_times JOIN crop ON seeding_times.crop_id = crop.id";
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

}
