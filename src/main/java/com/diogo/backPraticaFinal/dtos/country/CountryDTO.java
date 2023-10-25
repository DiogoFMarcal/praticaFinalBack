package com.diogo.backPraticaFinal.dtos.country;

public class CountryDTO {
	
	private String countryName;
	
	public CountryDTO(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}	
}
