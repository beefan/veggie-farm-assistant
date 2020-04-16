package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCNotificationDAO implements NotificationDAO{
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCNotificationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public String compileNotification() {

		String message = "=====DAILY HARVEST-ASSIST NOTIFICATION=====\n\n";
		message += getAllOfTodaysPlantings();
		message += getAllOfTodaysTransplants();
		message += getTodaysHarvests();
		message += getTomorrowsExpirations();
		
		
		return message;
	}

	@Override
	public String getAllOfTodaysPlantings() {
		String x = "Here are today's plantings: \n";

		String sql = "select crop_name, name from bed " + 
				"join field on field.id = bed.field_id " + 
				"where planting_date = current_date;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
				x += "-> The " + results.getString("crop_name") +  " is ready to plant in " + results.getString("name") + ". \n";
		}
		x += "\n";
		
		
		return x;
	}

	@Override
	public String getAllOfTodaysTransplants() {
		String x = "Here are the transplants to seed today: \n";

		String sql = "select crop_name, name from bed " + 
				"join field on field.id = bed.field_id " + 
				"where transplant_date = current_date;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
				x += "-> The " + results.getString("crop_name") + " transplants need to be seeded for " + results.getString("name") +  ". \n";
		}
		x += "\n";
		return x;
	}

	@Override
	public String getTodaysHarvests() {
		String sql = "select crop.crop_name, field.name, planting_date from bed " + 
				"join crop on crop.crop_name = bed.crop_name " + 
				"join seeding_times on crop.id = seeding_times.crop_id " + 
				"join field on field.id = bed.field_id " + 
				"where " + 
				"case when transplant_date is null then age(planting_date + direct_seed_to_harvest) = '00:00:00' " + 
				"else age(planting_date + transplant_to_harvest) = '00:00:00' end;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		String x = "Here are the crops that need harvesting today: \n";
		while (results.next()) {
				x += "-> The " + results.getString("crop_name") + " planted in " + results.getString("name") +  " on " + results.getDate("planting_date").toString() + " is ready for harvest. \n";
		}
		x += "\n";
		return x;
	}

	@Override
	public String getTomorrowsExpirations() {
		String sql = "select crop.crop_name, harvest_date from harvest  " + 
				"join crop on crop.id = harvest.crop_id   " + 
				"join expiration on crop.id = expiration.crop_id   " +  
				"where age(harvest_date + days_until_expire, current_date + interval '1 days') = '00:00:00';";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		String x = "Here are the crops that expire tomorrow if you haven't sold them: \n";
		while (results.next()) {
				x += "-> The " + results.getString("crop_name") + " harvested on " + results.getDate("harvest_date").toString() + " is expiring. \n";	
		}
		x += "\n";
		return x;
	}

}
