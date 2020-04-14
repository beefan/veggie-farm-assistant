package com.techelevator;

import java.time.LocalDate;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.JDBCBedDAO;
import com.techelevator.model.JDBCChartDAO;
import com.techelevator.model.JDBCHarvestDAO;
import com.techelevator.model.JDBCSaleDAO;
import com.techelevator.model.JDBCSeedingTimeDAO;

public class JDBCChartDAOTest extends DAOIntegrationTest{

	
	private static SingleConnectionDataSource dataSource;
	private JDBCChartDAO dao;
	private JdbcTemplate jdbcTemplate;
	private JDBCHarvestDAO harvest;
	private JDBCSaleDAO sale;
	private JDBCBedDAO bed;
	private int fieldId;
	private int bedId;
	private int id;
	
	@Before 
	public void setup() {
		dataSource = (SingleConnectionDataSource)
		super.getDataSource();
		dao = new JDBCChartDAO(dataSource);
		harvest = new JDBCHarvestDAO(dataSource);
		bed = new JDBCBedDAO(dataSource);
		sale = new JDBCSaleDAO(dataSource);
		jdbcTemplate=new JdbcTemplate(dataSource);
		truncate();
		jdbcTemplate.update("INSERT INTO crop (crop_name) VALUES ('Apples')");
		bed.saveField("user",  "dirt");
		SqlRowSet results = jdbcTemplate.queryForRowSet("select id from field where name = ? and username = ?", "dirt", "user");
		results.next();
		fieldId = results.getInt("id");
		bed.saveBed(fieldId, "Apples", LocalDate.now());
		SqlRowSet results2 = jdbcTemplate.queryForRowSet("select id from bed where field_id = ?", fieldId);
	    results2.next();
		bedId= results2.getInt("id");
		harvest.saveHarvest("Apples", bedId, 1);
		sale.saveSale("Apples", 1.5, "Yep");
		String sql = "select id from crop where crop_name = \'Apples\'";
		SqlRowSet results3 = jdbcTemplate.queryForRowSet(sql);
		results3.next();
		id = (results3.getInt("id"));
		
	}
	
	@Test
	public void test_get_crop_by_sale_data() {
		Assert.assertEquals(1, dao.getCropsWithSalesData().size());
	}
	
	@Test
	public void test_get_crop_by_harvest_data() {
		Assert.assertEquals(1, dao.getCropsWithHarvestData().size());
	}
	
	@Test
	public void test_get_sale_data_by_crop() {
		Assert.assertEquals(1, dao.sevenDayHarvestByCrop(id).size());
	}
	
	@Test
	public void test_the_other_one() {
		Assert.assertEquals(1, dao.sevenDaySalesByCrop(id).size());
	}
	
	private void truncate() {
		String sql = "truncate crop cascade";
		jdbcTemplate.update(sql);
		sql = "truncate harvest cascade";
		jdbcTemplate.update(sql);
		sql = "truncate sales cascade";
		jdbcTemplate.update(sql);
		sql = "truncate bed cascade";
		jdbcTemplate.update(sql);
	}
	
	
}
