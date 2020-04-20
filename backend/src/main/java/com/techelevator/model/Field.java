package com.techelevator.model;

import java.util.List;

public class Field {
	
	private String name;
	
	private String username;
	
	private int id;
	
	private List<Bed> beds;

	public List<Bed> getBeds() {
		return beds;
	}

	public void setBeds(List<Bed> beds) {
		this.beds = beds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
