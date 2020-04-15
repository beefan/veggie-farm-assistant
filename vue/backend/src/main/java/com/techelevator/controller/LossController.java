package com.techelevator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Loss;
import com.techelevator.model.LossDAO;

@RestController
@RequestMapping("/api/loss")
@CrossOrigin
public class LossController {

	
	@Autowired
	private LossDAO lossDao;
	
	@PostMapping
	public void saveLoss(@RequestBody Loss loss) {
		System.out.print(loss.getCropName());
		System.out.print(loss.getDollarAmount());
		System.out.print(loss.getWeightAmount());
		System.out.print(loss.getLossType());
	 lossDao.saveLossInfo(loss.getCropName(), loss.getDollarAmount(), loss.getWeightAmount(), loss.getLossType());
	
	
	}
	
	
	
	
}
