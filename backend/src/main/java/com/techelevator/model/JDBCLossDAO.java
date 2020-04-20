package com.techelevator.model;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCLossDAO  implements LossDAO{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCLossDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void saveLossInfo(String cropName, double dollarAmount, double weightAmount, String lossType) {
		String sql = "Select id from crop where crop_name =?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cropName);
		results.next();
		int cropId = results.getInt("id");
		sql = "Insert into loss (crop_id,weight_info,loss_type,dollar_amount, loss_date) values (?,?,?,?, ?);";
		jdbcTemplate.update(sql, cropId, weightAmount, lossType, dollarAmount, LocalDate.now());
		
	}

}
