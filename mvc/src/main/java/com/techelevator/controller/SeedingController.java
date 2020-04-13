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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.SeedingTime;
import com.techelevator.model.SeedingTimesDAO;

@RestController
@RequestMapping("/api/seedingtimes")
@CrossOrigin 
public class SeedingController {
 
	@Autowired
	private SeedingTimesDAO seedingTimeDao;
	
	@GetMapping
	public List<SeedingTime> seedinglist() {
		return seedingTimeDao.getAllSeedingTimes();
	}

	@PostMapping()
	public void saveSeedingTimes(@RequestBody List<SeedingTime> seedingTimes) {
		
		for (SeedingTime st : seedingTimes) {
			if (st.getDirectSeedToHarvestInDays() == 0) {
				seedingTimeDao.save(st.getCropName(), st.getDirectSeedToTransplantInDays(),
						st.getTransplantToHarvestInDays());
			} else if (st.getDirectSeedToTransplantInDays() == 0 && st.getTransplantToHarvestInDays() == 0) {
				seedingTimeDao.save(st.getCropName(), st.getDirectSeedToHarvestInDays());
			} else {
				seedingTimeDao.save(st.getCropName(), st.getDirectSeedToTransplantInDays(),
						st.getTransplantToHarvestInDays());
				seedingTimeDao.save(st.getCropName(), st.getDirectSeedToHarvestInDays());
			}
		}
	}
	
	@DeleteMapping("{cropName}")
	public void deteSeedingTime(@PathVariable String cropName) {
		seedingTimeDao.delete(cropName);
	}
	
}
