package com.diogo.backPraticaFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.diogo.backPraticaFinal.models.Coin;
import com.diogo.backPraticaFinal.models.Wallet;
import com.diogo.backPraticaFinal.models.WalletEntry;
import com.diogo.backPraticaFinal.repositories.WalletRepository;

@Service
public class WalletService {

	//############################################################################################

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private CoinService coinService;

	@Autowired
	private WalletEntryService walletEntryService;

	//############################################################################################

	public Wallet create(Wallet wallet) {
		//when i create a wallet i must create all the walletentries

		Wallet savedWallet = walletRepository.save(wallet);

		List<Coin> coins = coinService.getAll();

		for(Coin c: coins) {
			double qnt = 0.0;
			if(c.getName().equals("Euro") ) {
				qnt=10000000;
			}

			walletEntryService.create(new WalletEntry(savedWallet, c, qnt));
		}

		return savedWallet;
	}


	public List<Wallet> getAll() {
		return walletRepository.findAll();
	}

	public Wallet getWalletByUserId(Integer userId) {
		Optional<Wallet> result = walletRepository.findByUserId(userId);

		if(result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found");
		}
		return result.get();
	}
}