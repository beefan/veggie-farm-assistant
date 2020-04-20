package com.techelevator.model;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCSaleDAO implements SaleDAO{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCSaleDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void saveSale(String cropName, double dollarAmount, String saleType) {
		String sql = "SELECT id FROM crop WHERE crop_name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cropName);
		results.next();
		int cropId = results.getInt("id");
		sql = "INSERT INTO sales (crop_id, dollar_amount, sale_type, sale_date) VALUES (?,?,?,?)";
		jdbcTemplate.update(sql, cropId, dollarAmount, saleType, LocalDate.now());
		
	}

}
