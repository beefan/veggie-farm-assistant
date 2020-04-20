package com.techelevator.model;

import java.time.LocalDate;

public class Sale {

		private String cropName;
		
		private double dollarAmount;
		
		private String saleType;
		
		private LocalDate saleDate;

		public LocalDate getSaleDate() {
			return saleDate;
		}

		public void setSaleDate(LocalDate saleDate) {
			this.saleDate = saleDate;
		}

		public String getCropName() {
			return cropName;
		}

		public void setCropName(String cropName) {
			this.cropName = cropName;
		}

		public double getDollarAmount() {
			return dollarAmount;
		}

		public void setDollarAmount(double dollarAmount) {
			this.dollarAmount = dollarAmount;
		}

		public String getSaleType() {
			return saleType;
		}

		public void setSaleType(String saleType) {
			this.saleType = saleType;
		}
		
		
		
		
		
	}


