package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Harvest;
import com.techelevator.model.HarvestDAO;

@RestController
@RequestMapping("/api/harvest")
@CrossOrigin
public class HarvestController {

	@Autowired
	private HarvestDAO harvestDao;
	
	
	@GetMapping("/{bedId}")
	public List<Harvest> harvestList(@PathVariable int bedId){
		return harvestDao.getAllHarvestsByBeds(bedId);
	}
	
	@PostMapping()
	public void saveHarvests(@RequestBody Harvest harvest) {
		if(harvest.getCropCount() == 0) {
			harvestDao.saveHarvest(harvest.getCropName(), harvest.getBedId(), harvest.getCropWeight());
		} else {
		harvestDao.saveHarvest(harvest.getCropName(), harvest.getBedId(), harvest.getCropWeight(), harvest.getCropCount());
	}
	
	
}
	@DeleteMapping()
	public void deleteHarvest(@PathVariable int harvestId) {
		harvestDao.deleteHarvest(harvestId);
	}
	
}
