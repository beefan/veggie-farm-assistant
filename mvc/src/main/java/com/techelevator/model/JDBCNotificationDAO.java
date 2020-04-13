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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllOfTodaysPlantings() {
		String x = "Here are today's plantings: \n";

		String sql = "select crop_name, name from bed " + 
				"join field on field.id = bed.field_id " + 
				"where planting_date = current_date;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			if (results.isLast() && !results.isFirst()) {
				x += ", and you need to plant " + results.getString("crop_name") + " in " + results.getString("name") + " today";
			} else if (results.isFirst()) {
				x += "You need to plant " + results.getString("crop_name") + " in " + results.getString("name") + " today";
			}
			else {
			x += ", you need to plant " + results.getString("crop_name") + " in " + results.getString("name") + " today";
			}
		}
		x += ". \n";
		
		
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
			if (results.isLast() && !results.isFirst()) {
				x += ", and you need to seed " + results.getString("crop_name") + " for " + results.getString("name") + " today";
			} else if (results.isFirst()) {
				x += "You need to seed " + results.getString("crop_name") + " for " + results.getString("name") +  " today";
			}
			else {
			x += ", you need to seed " + results.getString("crop_name") + " for " + results.getString("name") +  " today";
			}
		}
		x += ".";
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
			if (results.isLast() && !results.isFirst()) {
				x += ", and you need to harvest the " + results.getString("crop_name") + " that you planted in " + results.getString("name") +  " on " + results.getDate("planting_date").toString();
			} else if (results.isFirst()) {
				x += "You need to harvest the " + results.getString("crop_name") + " that you planted in " + results.getString("name") +  " on " + results.getDate("planting_date").toString();
			}
			else {
			x += ", you need to harvest the " + results.getString("crop_name") + " that you planted in " + results.getString("name") +  " on " + results.getDate("planting_date").toString();
			}
		}
		x += ".";
		return x;
	}

	@Override
	public String getTomorrowsExpirations() {
		// TODO Auto-generated method stub
		return null;
	}

}
