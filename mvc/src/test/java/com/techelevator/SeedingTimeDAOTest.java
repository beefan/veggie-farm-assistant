package com.techelevator;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.JDBCSeedingTimeDAO;

public class SeedingTimeDAOTest extends DAOIntegrationTest{
	
	private static SingleConnectionDataSource dataSource;
	private JDBCSeedingTimeDAO dao;
	private JdbcTemplate jdbcTemplate;
	
	
	@Before 
	public void setup() {
		dataSource = (SingleConnectionDataSource)
		super.getDataSource();
		dao = new JDBCSeedingTimeDAO(dataSource);
		jdbcTemplate=new JdbcTemplate(dataSource);
		truncate();
	}
	
	
	@Test
	public void save_new_crop_with_seeding_time() {
		dao.save("Corn", 103);
		String sql= "Select * FROM crop WHERE crop_name = 'Corn' ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		Assert.assertTrue(result.next());
		sql = "Select * FROM seeding_times ";
		Assert.assertTrue(jdbcTemplate.queryForRowSet(sql).next());
	}
	
	
	@Test
	public void insert_duplicate_crop_verify_change() {
		dao.save("Corn", 103);
		dao.save("Corn", 106);
		String sql = "Select direct_seed_to_harvest FROM seeding_times";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while(result.next()) {
		Assert.assertEquals(106, result.getInt("direct_seed_to_harvest") );
		}
	}
	
	
	@Test
	public void insert_duplicate_crop_verify_change_for_second_save_method() {
		dao.save("Tomatoes", 4, 5);
		dao.save("Tomatoes", 5, 6);
		String sql = "Select direct_seed_to_transplant, transplant_to_harvest FROM seeding_times";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while(result.next()) {
		Assert.assertEquals(5, result.getInt("direct_seed_to_transplant"));
		Assert.assertEquals(6, result.getInt("transplant_to_harvest"));
		}
	}
	
	
	@Test
	public void save_new_crop_with_seeding_time_for_second_save_method() {
		dao.save("Tomatoes", 4, 5);
		String sql= "Select * FROM crop WHERE crop_name = 'Tomatoes' ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		Assert.assertTrue(result.next());
		sql = "Select * FROM seeding_times ";
		Assert.assertTrue(jdbcTemplate.queryForRowSet(sql).next());
	}
	
	
	@Test
	public void get_all_seeding_times_from_db() {
		dao.save("Potatoes", 100);
		dao.save("Potatoes", 105, 106);
		Assert.assertEquals(1, dao.getAllSeedingTimes().size());
	}
	
	
	@Test
	public void get_all_seeding_times_from_db_with_null_values() {
		dao.save("Potatoes", 100);
		Assert.assertEquals(1, dao.getAllSeedingTimes().size());
	}

	
	
	
	
	
	
	private void truncate() {
		String sql = "truncate crop cascade";
		jdbcTemplate.update(sql);
		sql = "truncate seeding_times cascade";
		jdbcTemplate.update(sql);
	}
}
