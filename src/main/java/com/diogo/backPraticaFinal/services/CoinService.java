package com.diogo.backPraticaFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.diogo.backPraticaFinal.models.Coin;
import com.diogo.backPraticaFinal.repositories.CoinRepository;

@Service
public class CoinService {

	//############################################################################################

	@Autowired
	private CoinRepository coinRepository;

	//############################################################################################

	public Coin create(Coin coin) {
		return coinRepository.save(coin);
	}

	public List<Coin> getAll(){
		return coinRepository.findAll();
	}

	public Coin getByName(String coinName) {
		Optional<Coin> result = coinRepository.findByName(coinName);

		if(result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Coin with name %s not found", coinName));
		}
		return result.get();
	}

	public Coin findCoinById(int coinId) {
		Optional<Coin> result = coinRepository.findById(coinId);
		if(result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Coin with id %d not found.", coinId));
		}

		return result.get();
	}

	public List<Coin> findAll(){
		return coinRepository.findAll();
	}

	public void update(Coin coin) {
		coinRepository.save(coin);
	}
}
