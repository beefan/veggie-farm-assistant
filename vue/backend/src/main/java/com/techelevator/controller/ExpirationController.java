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

import com.techelevator.model.Expiration;
import com.techelevator.model.ExpirationDAO;

@RestController
@RequestMapping("/api/expiration")
@CrossOrigin
public class ExpirationController {

	@Autowired
	private ExpirationDAO expirationDao; 
	
	@GetMapping
	public List<Expiration> expirationList(){
		return expirationDao.getAllExpirationTimes();
	}
	
	
	@PostMapping()
	public void saveExpirationTimes(@RequestBody List<Expiration> expirationtime) {
		for(Expiration ex : expirationtime) {
			expirationDao.save(ex.getCropName(), ex.getDaysToExpiration()); 
		}
		
	}  
	
	@DeleteMapping("{cropName}")
	public void deleteExpirationTime(@PathVariable String cropName) {
		expirationDao.delete(cropName);
	}
	
}
