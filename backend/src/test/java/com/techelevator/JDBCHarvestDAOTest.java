package com.techelevator;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.JDBCBedDAO;
import com.techelevator.model.JDBCHarvestDAO;

import junit.*;

public class JDBCHarvestDAOTest extends DAOIntegrationTest{

	protected static SingleConnectionDataSource dataSource;
	protected JDBCHarvestDAO dao;
	protected JdbcTemplate jdbcTemplate;
	protected JDBCBedDAO bed;
	protected int fieldId;
	protected int bedId;
	
	@Before
	public void setup() {
		dataSource = (SingleConnectionDataSource)
				super.getDataSource();
				dao = new JDBCHarvestDAO(dataSource);
				bed = new JDBCBedDAO(dataSource);
				jdbcTemplate=new JdbcTemplate(dataSource);
				truncate();
				jdbcTemplate.update("INSERT INTO users (\"username\", \"password\", \"salt\", \"role\") VALUES ('user', 'FjZDm+sndmsdEDwNtfr6NA==', 'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=', 'user')");
				jdbcTemplate.update("INSERT INTO crop (crop_name) VALUES ('Apples')");
				bed.saveField("user", "dirt");
				SqlRowSet results = jdbcTemplate.queryForRowSet("select id from field where name = ? and username = ?", "dirt", "user");
				results.next();
				fieldId = results.getInt("id");
				bed.saveBed(fieldId, "Apples", LocalDate.now(), LocalDate.now());
				SqlRowSet results2 = jdbcTemplate.queryForRowSet("select id from bed where field_id = ?", fieldId);
			    results2.next();
			    
				bedId= results2.getInt("id");
				
				
				
	}
	
	@Test
	public void harvest_save() {
		dao.saveHarvest("Apples", bedId, 50);
		Assert.assertEquals(1,dao.getAllHarvestsByBeds(bedId).size());
	}
	
	@Test
	public void harvest_second_save() {
		dao.saveHarvest("Apples", bedId, 100, 100);
		Assert.assertEquals(1,dao.getAllHarvestsByBeds(bedId).size());
	}
	
	@Test
	public void save_multiple() {
		dao.saveHarvest("Apples", bedId, 500,500);
		dao.saveHarvest("Apples", bedId, 500,500);
		Assert.assertEquals(2,dao.getAllHarvestsByBeds(bedId).size());
	}
	
	@Test
	public void delete_multiple() {
		dao.saveHarvest("Apples", bedId, 500,500);
		dao.saveHarvest("Apples", bedId, 500,500);
		String sql = "SELECT id from harvest WHERE bed_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bedId);
		results.next();
		int harvestId = results.getInt("id");
		dao.deleteHarvest(harvestId);
		Assert.assertEquals(1,dao.getAllHarvestsByBeds(bedId).size());
	}
	
	
	private void truncate() {
		String sql = "truncate harvest cascade";
		jdbcTemplate.update(sql);
		sql = "truncate loss cascade";
		jdbcTemplate.update(sql);
		sql = "truncate bed cascade";
		jdbcTemplate.update(sql);
		sql = "truncate crop cascade";
		jdbcTemplate.update(sql);
		sql = "truncate users cascade";
		jdbcTemplate.update(sql);
	}
	
}
