package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface BedDAO {
	
	public void saveBed(int fieldId, String cropName, LocalDate plantingDate, LocalDate transplantDate);

	public void saveBed(int fieldId, String cropName, LocalDate plantingDate);
	
	public void updateBed(int bedId, int fieldId, String cropName, LocalDate plantingDate, LocalDate transplantDate);
	
	public void updateBed(int bedId, int fieldId, String cropName, LocalDate plantingDate);
	
	public void deleteBed(int bedId);
	
	public void saveField(String username, String fieldName);
	
	public void updateField(int id, String fieldName);
	
	public void deleteField( int id);
	
	public List<Bed> getAllBedsByField(int fieldId);
	
	public List<Field> getAllFields(String username);
	
	public void resetField(int fieldId);
	
	public List<String> getAllCrops();
}
