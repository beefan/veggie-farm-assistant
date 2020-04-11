package com.techelevator;

import java.time.LocalDate;

import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.JDBCBedDAO;
import com.techelevator.model.JDBCSeedingTimeDAO;

import org.junit.*;

public class BedDAOTest extends DAOIntegrationTest{
	
	private static SingleConnectionDataSource dataSource;
	private JDBCBedDAO dao;
	private JdbcTemplate jdbcTemplate;
	private int fieldId;
	private int fieldId2;
	
	@Before 
	public void setup() {
		dataSource = (SingleConnectionDataSource)
		super.getDataSource();
		dao = new JDBCBedDAO(dataSource);
		jdbcTemplate=new JdbcTemplate(dataSource);
		truncate();
		jdbcTemplate.update("INSERT INTO users (\"username\", \"password\", \"salt\", \"role\") VALUES ('user', 'FjZDm+sndmsdEDwNtfr6NA==', 'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=', 'user')");
		dao.saveField("user", "dirt");
		dao.saveField("user", "mud");
		SqlRowSet results = jdbcTemplate.queryForRowSet("select id from field where name = ? and username = ?", "dirt", "user");
		results.next();
		fieldId = results.getInt("id");
		SqlRowSet result = jdbcTemplate.queryForRowSet("select id from field where name = ? and username = ?", "mud", "user");
		result.next();
		fieldId2 = result.getInt("id");
		dao.saveBed(fieldId, "Dong Trees", LocalDate.now());
		dao.saveBed(fieldId, "Eggplant", LocalDate.now(), LocalDate.now());
	}
	
	@Test
	public void get_all_crops() {
		Assert.assertEquals(2, dao.getAllCrops().size());
		
	}
	
	@Test
	public void testing_save_field_method() {
		Assert.assertEquals(2, dao.getAllFields("user").size());
		System.out.print(fieldId);
	}
	
	@Test
	public void testing_if_the_two_bed_saves_worked() {
		Assert.assertEquals(2, dao.getAllBedsByField(fieldId).size());
	}
	
	@Test
	public void test_first_update_method() {
		SqlRowSet results = jdbcTemplate.queryForRowSet("select id from bed where crop_name = ?", "Dong Trees");
		results.next();
		int bedId = results.getInt("id");
		dao.updateBed(bedId, fieldId2, "Dong Trees", LocalDate.now());
		
		Assert.assertEquals(1, dao.getAllBedsByField(fieldId2).size());
		
	}
	
	@Test
	public void test_second_update_method() {
		SqlRowSet results = jdbcTemplate.queryForRowSet("select id from bed where crop_name = ?", "Eggplant");
		results.next();
		int bedId = results.getInt("id");
		
		dao.updateBed(bedId, fieldId2, "Eggplant", LocalDate.now(), LocalDate.now());
		Assert.assertEquals(1, dao.getAllBedsByField(fieldId2).size());
	}
	
	@Test
	public void testing_delete_bed_method() {
		SqlRowSet results = jdbcTemplate.queryForRowSet("select id from bed where crop_name = ?", "Eggplant");
		results.next();
		int bedId = results.getInt("id");
		dao.deleteBed(bedId);
		Assert.assertEquals(1, dao.getAllBedsByField(fieldId).size());
	}
	
	@Test
	public void testing_the_update_field_method() {
		dao.updateField(fieldId, "It's a field");
		String sql = "select name from field where id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, fieldId);
		results.next();
		String test = results.getString("name");
		Assert.assertEquals("It's a field", test);
	}
	
	@Test
	public void testing_the_delete_field_method() {
		dao.deleteField(fieldId);
		Assert.assertEquals(1, dao.getAllFields("user").size());
	}
	
	
	
	private void truncate() {
		String sql = "truncate bed cascade";
		jdbcTemplate.update(sql);
		sql = "truncate field cascade";
		jdbcTemplate.update(sql);
		sql = "truncate users cascade";
		jdbcTemplate.update(sql);
		sql = "truncate crop cascade";
		jdbcTemplate.update(sql);
	}

}
