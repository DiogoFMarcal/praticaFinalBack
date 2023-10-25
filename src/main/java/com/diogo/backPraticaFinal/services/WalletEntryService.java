package com.diogo.backPraticaFinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.diogo.backPraticaFinal.models.WalletEntry;
import com.diogo.backPraticaFinal.repositories.WalletEntryRepository;

@Service
public class WalletEntryService {

	//############################################################################################

	@Autowired
	private WalletEntryRepository walletEntryRepository;

	//############################################################################################

	public WalletEntry create(WalletEntry walletEntry) {
		return walletEntryRepository.save(walletEntry);
	}

	public List<WalletEntry> getAll(){
		return walletEntryRepository.findAll();
	}

	public List<WalletEntry> getWalletEntriesByWalletId(Integer walletId) {
		return walletEntryRepository.findByWalletId(walletId);
	}

	public WalletEntry getEuroWalletEntriyByWalletId(Integer walletId) {
		return walletEntryRepository.findEuroWalletEntryByUserId(walletId).get();
	}

	public void update(WalletEntry walletEntry) {
		walletEntryRepository.save(walletEntry);
	}

	public WalletEntry getByWalletIdAndCoinName(Integer walletId, String coinName) {
		Optional<WalletEntry> result = walletEntryRepository.findByWalletIdAndCoinName(walletId, coinName);
		if(result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("WalletEntry not found for wallet id %d and "
					+ "coin name %s", walletId, coinName));
		}
		return result.get();
	}

	public WalletEntry getWalletEntriyByWalletIdAndCoinId(Integer walletId, Integer coinId) {

		Optional<WalletEntry> result = walletEntryRepository.findByWalletIdAndCoinId(walletId, coinId);

		if(result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Wallet Entry not found for coin id %d.", coinId));
		}

		return result.get();
	}
}
