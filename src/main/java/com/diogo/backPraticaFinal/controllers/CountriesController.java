package com.diogo.backPraticaFinal.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diogo.backPraticaFinal.dtos.country.CountryDTO;
import com.diogo.backPraticaFinal.services.CountriesService;

@RestController
public class CountriesController {
	
	@Autowired
	private CountriesService countriesService;
	
	@GetMapping("/countries")
	public ResponseEntity<List<CountryDTO>> getCountries(){
		return new ResponseEntity<List<CountryDTO>>(countriesService.getCountries(), HttpStatus.OK);
	}
	
	
}
