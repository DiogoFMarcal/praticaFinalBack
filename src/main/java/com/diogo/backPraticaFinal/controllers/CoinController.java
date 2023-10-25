package com.diogo.backPraticaFinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.diogo.backPraticaFinal.models.Coin;
import com.diogo.backPraticaFinal.services.CoinService;

@RestController
public class CoinController {

	//############################################################################################

	@Autowired
	private CoinService coinService;

	//############################################################################################

	@GetMapping("/myApp/coin/{coinName}")
	public ResponseEntity<Coin> getCoinByName(@PathVariable("coinName") String coinName){
		return new ResponseEntity<Coin>(coinService.getByName(coinName), HttpStatus.OK);
	}

	@GetMapping("/myApp/coin")
	public ResponseEntity<List<Coin>> getCoins(){
		return new ResponseEntity<List<Coin>>(coinService.getAll(), HttpStatus.OK);
	}

	@GetMapping("myApp/check-coin-exists/{coinId}")
	public ResponseEntity<Coin> checkCoinExistsById ( @PathVariable("coinId") int coinId) {

		//the following call will return an Response status exception if there is not a coin with the given id
		Coin coinToSend = coinService.findCoinById(coinId);

		//if no exception is raised then we retun ok to the front
		return new ResponseEntity<Coin>(coinToSend, HttpStatus.OK);
	}
}
