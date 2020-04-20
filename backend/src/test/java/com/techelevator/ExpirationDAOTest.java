package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.JDBCExpirationDAO;
import com.techelevator.model.JDBCSeedingTimeDAO;

public class ExpirationDAOTest extends DAOIntegrationTest {

	
	private static SingleConnectionDataSource dataSource;
	private JDBCExpirationDAO dao;
	private JdbcTemplate jdbcTemplate;
	
	@Before 
	public void setup() {
		dataSource = (SingleConnectionDataSource)
		super.getDataSource();
		dao = new JDBCExpirationDAO(dataSource);
		jdbcTemplate=new JdbcTemplate(dataSource);
		truncate();
	}
	
	@Test
	public void get_all_expirations() {
		dao.save("Potatoes", 100);
		
		Assert.assertEquals(1, dao.getAllExpirationTimes().size());
	}
	
	@Test
	public void save_expirations() {
		dao.save("Potatoes", 100);
		dao.save("onion", 100);
		Assert.assertEquals(2, dao.getAllExpirationTimes().size());
	}
	
	@Test
	public void expiration_delete() {
		dao.save("Onions", 50);
		dao.delete("Onions");
		Assert.assertEquals(0, dao.getAllExpirationTimes().size());
	}
	
	
	

	private void truncate() {
		String sql = "truncate crop cascade";
		jdbcTemplate.update(sql);
		sql = "truncate expiration cascade";
		jdbcTemplate.update(sql);
		
	}
	
}
