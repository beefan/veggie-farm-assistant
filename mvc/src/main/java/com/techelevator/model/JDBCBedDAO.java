package com.techelevator.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCBedDAO implements BedDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCBedDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveBed(int fieldId, String cropName, LocalDate plantingDate, LocalDate transplantDate) {
		
		String sql = "insert into bed (crop_name, planting_date, transplant_date, field_id) values (?, ?, ?, ?)";
		jdbcTemplate.update(sql, cropName, plantingDate, transplantDate, fieldId);

	}

	@Override
	public void saveBed(int fieldId, String cropName, LocalDate plantingDate) {
		String sql = "insert into bed (crop_name, planting_date, field_id) values (?, ?, ?)";
		jdbcTemplate.update(sql, cropName, plantingDate, fieldId);		
	}
	
	@Override
	public void updateBed(int bedId, int fieldId, String cropName, LocalDate plantingDate,
			LocalDate transplantDate) {
		String sql = "update bed set field_id = ?, crop_name = ?, planting_date = ?, transplant_Date = ? where id = ?";
		jdbcTemplate.update(sql, fieldId, cropName, plantingDate, transplantDate, bedId);
	}

	@Override
	public void updateBed(int bedId, int fieldId, String cropName, LocalDate plantingDate) {
		String sql = "update bed set field_id = ?, crop_name = ?, planting_date = ? where id = ?";
		jdbcTemplate.update(sql, fieldId, cropName, plantingDate, bedId);
		
	}

	@Override
	public void deleteBed(int bedId) {
		String sql = "delete from bed where id = ?";
		jdbcTemplate.update(sql, bedId);
		
	}

	@Override
	public void saveField(String username, String fieldName) {
		String sql = "insert into field (name, username) values (?, ?)";
		jdbcTemplate.update(sql, fieldName, username);
	}
	
	@Override
	public void updateField(int id, String fieldName) {
		String sql = "update field set name = ? where id = ?";
		jdbcTemplate.update(sql, fieldName, id);
		
	}

	@Override
	public void deleteField(String username, int id) {
		String sql = "delete from bed where field_id = ?";
		jdbcTemplate.update(sql, id);
		
		sql = "delete from field where username = ? and id = ?";
		jdbcTemplate.update(sql, username, id);
		
	}

	@Override
	public List<Bed> getAllBedsByField(int fieldId) {
		List<Bed> beds = new ArrayList<Bed>();
		String sql = "select id, crop_name, planting_date, transplant_date from bed where field_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, fieldId);
		
		while(results.next()) {
			Bed bed = new Bed();
			bed.setBedId(results.getInt("id"));
			bed.setCropName(results.getString("crop_name"));
			bed.setPlantingDate(results.getDate("planting_date").toLocalDate());
			try {
				bed.setTransplantDate(results.getDate("transplant_date").toLocalDate());
			} catch (NullPointerException e) {
				//don't set
			}
			beds.add(bed);
			
		}
		return beds;
	}

	@Override
	public List<Field> getAllFields(String username) {
		List<Field> fields = new ArrayList<Field>();
		String sql = "select * from field where username = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
		while(results.next()) {
			Field field = new Field();
			field.setName(results.getString("name"));
			field.setId(results.getInt("id"));
			fields.add(field);
		}
		return fields;
	}

	

	


}
