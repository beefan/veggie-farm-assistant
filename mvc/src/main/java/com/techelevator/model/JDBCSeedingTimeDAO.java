package com.techelevator.model;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		if (result.next()) {
			int cropId = result.getInt("id");
			sql = "update seeding_times set direct_seed_to_harvest = ? where id = ?;";
			jdbcTemplate.update(sql, directSeedToHarvestInDays, cropId);
		} else {
			sql = "insert into crop (crop_name) values (?) returning id;";
			int cropId = jdbcTemplate.queryForRowSet(sql).getInt("id");
			sql = "insert into seeding_times (crop_id, direct_seed_to_harvest) values (?, ?)";
			jdbcTemplate.update(sql, cropId, directSeedToHarvestInDays);
		}
		
	}

	@Override
	public void save(String cropName, int directSeedToTransplantInDays, int transplantToHarvestInDays) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SeedingTime> getAllSeedingTimes() {
		// TODO Auto-generated method stub
		return null;
	}

}
