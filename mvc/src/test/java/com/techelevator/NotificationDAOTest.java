package com.techelevator;

import java.time.LocalDate;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.JDBCBedDAO;
import com.techelevator.model.JDBCNotificationDAO;
import com.techelevator.model.JDBCSeedingTimeDAO;

public class NotificationDAOTest extends DAOIntegrationTest{

	private static SingleConnectionDataSource dataSource;
	private JDBCNotificationDAO dao;
	private JdbcTemplate jdbcTemplate;
	private JDBCBedDAO bed;
	private JDBCSeedingTimeDAO st;
	private int fieldId;
	private int bedId;
	
	@Before 
	public void setup() {
		dataSource = (SingleConnectionDataSource)
		super.getDataSource();
		dao = new JDBCNotificationDAO(dataSource);
		bed = new JDBCBedDAO(dataSource);
		st = new JDBCSeedingTimeDAO(dataSource);
		jdbcTemplate=new JdbcTemplate(dataSource);
		truncate();
		jdbcTemplate.update("INSERT INTO users (\"username\", \"password\", \"salt\", \"role\") VALUES ('user', 'FjZDm+sndmsdEDwNtfr6NA==', 'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=', 'user')");
		jdbcTemplate.update("INSERT INTO crop (crop_name) VALUES ('Apples')");
		bed.saveField("user", "dirt");
		SqlRowSet results = jdbcTemplate.queryForRowSet("select id from field where name = ? and username = ?", "dirt", "user");
		results.next();
		fieldId = results.getInt("id");
		bed.saveBed(fieldId, "Apples", LocalDate.now().minusDays(1));
		

		SqlRowSet results2 = jdbcTemplate.queryForRowSet("select id from bed where field_id = ?", fieldId);
	    results2.next();
	    
		bedId= results2.getInt("id");
		
	}
	
	@Test
	public void planting_date_notification_test() {
//		Assert.assertEquals("Hey, you need to plant Apples in dirt today.", dao.getAllOfTodaysPlantings());
		bed.saveBed(fieldId, "Bananas", LocalDate.now(), LocalDate.now());
//		Assert.assertEquals("Hey, you need to plant Bananas in dirt today, and you need to plant Apples in dirt today.", dao.getAllOfTodaysPlantings());
	System.out.println(dao.getAllOfTodaysPlantings());
	}
	
	@Test
	public void check_the_seeding_time_notification() {
		bed.saveBed(fieldId, "Bananas", LocalDate.now(), LocalDate.now());

		System.out.println(dao.getAllOfTodaysTransplants());
	}
	
	@Test
	public void check_harvest_notification() {
		st.save("Dong", 1);
		bed.saveBed(fieldId, "Dong", LocalDate.now().minusDays(1));
		st.save("Apples", 1, 1);
		st.save("Apples", 1);
		System.out.print(st.getAllSeedingTimes().size() + "what");
		
		//st.save(cropName, directSeedToHarvestInDays);
		//st.save(cropName, directSeedToTransplantInDays, transplantToHarvestInDays);
		System.out.println(dao.getTodaysHarvests());
	}
	
	private void truncate() {
		String sql = "truncate harvest cascade";
		jdbcTemplate.update(sql);
		sql = "truncate bed cascade";
		jdbcTemplate.update(sql);
		sql = "truncate crop cascade";
		jdbcTemplate.update(sql);
		sql = "truncate users cascade";
		jdbcTemplate.update(sql);
		sql = "truncate field cascade";
		jdbcTemplate.update(sql);
	}
	
}
