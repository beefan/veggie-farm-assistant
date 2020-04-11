package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.JDBCBedDAO;
import com.techelevator.model.JDBCHarvestDAO;
import com.techelevator.model.JDBCSaleDAO;

public class JDBCSaleDAOTest extends DAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCSaleDAO dao;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup() {
		
		dataSource = (SingleConnectionDataSource)
				super.getDataSource();
				dao = new JDBCSaleDAO(dataSource);
				jdbcTemplate = new JdbcTemplate(dataSource);
				truncate();
				jdbcTemplate.update("INSERT INTO crop (crop_name) VALUES ('Apples')");
				
	}
	
	@Test
	public void sale_save() {
		dao.saveSale("Apples", 500, "Retail");
		String sql = "SELECT crop_id FROM sales WHERE sale_type = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "Retail");
		Assert.assertTrue(results.next() );
		
	}
	private void truncate() {
		String sql = "truncate sales cascade";
		jdbcTemplate.update(sql);
		sql = "truncate crop cascade";
		jdbcTemplate.update(sql);
	}
	
}
