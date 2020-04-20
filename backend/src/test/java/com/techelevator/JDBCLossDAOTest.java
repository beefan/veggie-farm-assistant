package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.JDBCLossDAO;

public class JDBCLossDAOTest extends JDBCHarvestDAOTest {

	private JDBCLossDAO loss;
	
	@Before
	public void newSetup() {
		loss = new JDBCLossDAO(dataSource);
		loss.saveLossInfo("Apples", 50, 50, "Shrink");
		
	}
	
	@Test
	public void loss_save() {
		String sql = "SELECT id from loss";
		SqlRowSet results = super.jdbcTemplate.queryForRowSet(sql);
		Assert.assertTrue(results.next());
	}
}
