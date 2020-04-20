package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Bed;
import com.techelevator.model.BedDAO;
import com.techelevator.model.Field;

@RestController
@RequestMapping("/api/cropplanner")
@CrossOrigin
public class CropPlanController {
	
	@Autowired
	private BedDAO bedDao;
	
	@GetMapping("/crops")
	public List<String> cropList() {
		return bedDao.getAllCrops();
	}
	
	@GetMapping("/beds/{fieldId}")
	public List<Bed> bedList(@PathVariable int fieldId){
		return bedDao.getAllBedsByField(fieldId);
	}
	
	@GetMapping("{username}")
	public List<Field> fieldList(@PathVariable String username){
		return bedDao.getAllFields(username);
	}
	
	@PostMapping("/beds")
	public void saveCropPlanBed(@RequestBody List<Bed> bedList) {
		if (bedList.size() > 1) {
		bedDao.resetField(bedList.get(0).getFieldId());
		}
				
		for(Bed be: bedList) {
			
			if(be.getTransplantDate() == null) {
			bedDao.saveBed(be.getFieldId(), be.getCropName(), be.getPlantingDate());
			}else {
				bedDao.saveBed(be.getFieldId(), be.getCropName(), be.getPlantingDate(), be.getTransplantDate());
			}
		}
	}
	
	@PostMapping("/beds/update")
	public void updateCropPlanBed(@RequestBody Bed bed) {
		if(bed.getTransplantDate() == null) {
			bedDao.updateBed(bed.getBedId(), bed.getFieldId(), bed.getCropName(), bed.getPlantingDate());
		}else {
			bedDao.updateBed(bed.getBedId(), bed.getFieldId(), bed.getCropName(), bed.getPlantingDate(), bed.getTransplantDate());
		}
	}
	
	@PostMapping()
	public void SaveCropPlanField(@RequestBody Field field) {
		bedDao.saveField(field.getUsername(), field.getName());
	}
	@PostMapping("/field/update")
	public void updateFieldNme(@RequestBody Field field) {
		bedDao.updateField(field.getId(), field.getName());
	}
	
	@DeleteMapping("{fieldId}")
	public void deleteField(@PathVariable int fieldId) {
		bedDao.deleteField(fieldId);
	}
	
	@DeleteMapping("/beds/{bedId}")
	public void deleteBed(@PathVariable int bedId) {
		bedDao.deleteBed(bedId);
	}
	
	
	
}
