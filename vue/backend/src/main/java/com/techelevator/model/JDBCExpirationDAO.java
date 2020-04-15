package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCExpirationDAO implements ExpirationDAO{

	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public JDBCExpirationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void save(String cropName, int daysToExpiration) {
		int cropId;
		
		String sql = "select id from crop where crop_name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, cropName);
		
		if (!result.next()) {
			sql = "insert into crop (crop_name) values (?) returning id";
			result = jdbcTemplate.queryForRowSet(sql, cropName);
			result.next();	
		}
		
		cropId = result.getInt("id");
		
		
		sql = "select id from expiration where crop_id = ?";
		result = jdbcTemplate.queryForRowSet(sql, cropId);
		
		if (!result.next()) {
			sql = "insert into expiration (crop_id, days_until_expire) values (?, ?)";
			jdbcTemplate.update(sql, cropId, daysToExpiration);	
		}else {
			sql = "update expiration set days_until_expire = ? where crop_id = ?";
			jdbcTemplate.update(sql, daysToExpiration, cropId);
		} 
	}

	@Override
	public void delete(String cropName) {
		String sql = "delete from expiration where crop_id in (select id from crop where crop_name = ?);";
		jdbcTemplate.update(sql, cropName);
		
		//Todo cleanuo?
//		sql= "delete from crop where crop_name= ?";
//		jdbcTemplate.update(sql, cropName);
		
	}

	@Override
	public List<Expiration> getAllExpirationTimes() {
		List<Expiration> expiration = new ArrayList<Expiration>();
		String sql = "select crop_name, days_until_expire FROM expiration JOIN crop ON expiration.crop_id = crop.id order by crop_name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		
		while(results.next()) {
			Expiration ex = new Expiration();
			ex.setCropName(results.getString("crop_name"));
			ex.setDaysToExpiration(results.getInt("days_until_expire"));
			expiration.add(ex);
		}
		
		
		
		return expiration;
	}

}
