package com.diogo.backPraticaFinal.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.diogo.backPraticaFinal.dtos.requestsDTOs.BuyOrSellRequestDTO;
import com.diogo.backPraticaFinal.dtos.requestsDTOs.DepositRequestDTO;
import com.diogo.backPraticaFinal.dtos.requestsDTOs.TakeRequestDTO;
import com.diogo.backPraticaFinal.dtos.walletEntry.WalletEntryResponse;
import com.diogo.backPraticaFinal.models.Coin;
import com.diogo.backPraticaFinal.models.OperationType;
import com.diogo.backPraticaFinal.models.Transaction;
import com.diogo.backPraticaFinal.models.User;
import com.diogo.backPraticaFinal.models.Wallet;
import com.diogo.backPraticaFinal.models.WalletEntry;
import com.diogo.backPraticaFinal.services.CoinService;
import com.diogo.backPraticaFinal.services.TokenService;
import com.diogo.backPraticaFinal.services.TransactionService;
import com.diogo.backPraticaFinal.services.UserService;
import com.diogo.backPraticaFinal.services.WalletEntryService;
import com.diogo.backPraticaFinal.services.WalletService;

@RestController
public class WalletController {

	//############################################################################################
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private WalletEntryService walletEntryService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private UserService userService;

	@Autowired
	private CoinService coinService;

	@Autowired
	private TransactionService transactionService;
	
	//############################################################################################

	@GetMapping("myApp/wallet-entries")
	public ResponseEntity<List<WalletEntryResponse>> getUserWalletEntries(@RequestHeader("Authorization") 
	String authorizationHeader) {

		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		//recover the user that is making the request using the token service
		User user = tokenService.getUserByToken(token);

		//get the wallet id of the user
		Wallet w = walletService.getWalletByUserId(user.getId());

		//find wallet entries by wallet id
		List<WalletEntry> userWalletEntries = walletEntryService.getWalletEntriesByWalletId(w.getId());

		//convert the walletEntry list to ists reponse type
		List<WalletEntryResponse> walletEntriesResponse = new ArrayList<>();

		for(WalletEntry we : userWalletEntries) {
			walletEntriesResponse.add(new WalletEntryResponse(we.getCoin(), we.getQuantity()));
		}

		return new ResponseEntity<List<WalletEntryResponse>>(walletEntriesResponse, HttpStatus.OK);	

	}

	@PostMapping("/myApp/deposit")
	public ResponseEntity<Void> deposit(@RequestHeader("Authorization") 
	String authorizationHeader, @RequestBody DepositRequestDTO depositInfo ){

		//extract the token from the request header

		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		//get source and destination user of the deposit
		User destinationUser = tokenService.getUserByToken(token);
		
		//if there is not a user with that iban an error is launched to the front
		User sourceUser = userService.getUserByIban(depositInfo.getSourceIban());
		

		if( depositInfo.getSourceIban().equals( destinationUser.getIban() ) ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The source Iban cannot be the destination Iban.");
		}

		//get both wallets from the source user and the destination user
		Wallet destinationWallet = walletService.getWalletByUserId(destinationUser.getId());
		Wallet sourceWallet = walletService.getWalletByUserId(sourceUser.getId());

		//fetch source and destination wallet entry for euros
		WalletEntry destinationEuroWE =  walletEntryService.getEuroWalletEntriyByWalletId(destinationWallet.getId());
		WalletEntry sourceEuroWE =  walletEntryService.getEuroWalletEntriyByWalletId(sourceWallet.getId());

		//check if source walletEntry for euros has at least the same amount that is passed in the request

		if(sourceEuroWE.getQuantity() < depositInfo.getDepositValue()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid deposit value.");
		}

		//take the quantity of the deposit from the source and add it to the destination euro wallet entry

		sourceEuroWE.setQuantity(sourceEuroWE.getQuantity() - depositInfo.getDepositValue());
		destinationEuroWE.setQuantity(destinationEuroWE.getQuantity() + depositInfo.getDepositValue());

		//save both wallet entries for euro
		walletEntryService.update(sourceEuroWE);
		walletEntryService.update(destinationEuroWE);	

		// create the transaction
		transactionService.create( new Transaction( destinationUser, depositInfo.getDepositValue(), 
				OperationType.DEPOSIT,sourceEuroWE.getCoin(), sourceEuroWE.getCoin()) );

		return new ResponseEntity<Void>(HttpStatus.OK); 
	}

	@PostMapping("/myApp/take")
	public ResponseEntity<Void> take(@RequestHeader("Authorization") 
	String authorizationHeader, @RequestBody TakeRequestDTO takeInfo ){

		//extract the token from the request header
		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		//get source and destination user of the deposit
		User sourceUser = tokenService.getUserByToken(token);
		User destinationUser = userService.getUserByIban(takeInfo.getDestinationIban());


		if( takeInfo.getDestinationIban().equals( sourceUser.getIban() ) ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The source Iban cannot be the destination Iban.");
		}

		//get both wallets from the source user and the destination user
		Wallet destinationWallet = walletService.getWalletByUserId(destinationUser.getId());
		Wallet sourceWallet = walletService.getWalletByUserId(sourceUser.getId());

		//fetch source and destination wallet entry for euros
		WalletEntry destinationEuroWE =  walletEntryService.getEuroWalletEntriyByWalletId(destinationWallet.getId());
		WalletEntry sourceEuroWE =  walletEntryService.getEuroWalletEntriyByWalletId(sourceWallet.getId());

		//check if source walletEntry for euros has at least the same amount that is passed in the request

		if(sourceEuroWE.getQuantity() < takeInfo.getTakeValue()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid take value.");
		}

		//take the quantity of the deposit from the source and add it to the destination euro wallet entry

		sourceEuroWE.setQuantity(sourceEuroWE.getQuantity() - takeInfo.getTakeValue());
		destinationEuroWE.setQuantity(destinationEuroWE.getQuantity() + takeInfo.getTakeValue());

		//save both wallet entries for euro
		walletEntryService.update(sourceEuroWE);
		walletEntryService.update(destinationEuroWE);	

		// create the transaction
		transactionService.create( new Transaction( sourceUser, takeInfo.getTakeValue(), 
				OperationType.TAKE, sourceEuroWE.getCoin(), sourceEuroWE.getCoin()) );

		return new ResponseEntity<Void>(HttpStatus.OK); 
	}

	@PostMapping("/myApp/buy")
	public ResponseEntity<Void> buyCoin(@RequestHeader("Authorization") 
	String authorizationHeader, @RequestBody BuyOrSellRequestDTO buyInfo){

		//extract the token from the request header
		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		//get the user that is making the operation
		User user = tokenService.getUserByToken(token);

		Wallet userWallet = walletService.getWalletByUserId(user.getId());


		//get the wallet entry by the wallet id and coin name
		WalletEntry userWalletEntryForCoin =  walletEntryService.getByWalletIdAndCoinName(userWallet.getId(), buyInfo.getCoinName());
		
		WalletEntry userEuroWalletEntry =  walletEntryService.getEuroWalletEntriyByWalletId(userWallet.getId());

		Coin coin = coinService.getByName(buyInfo.getCoinName());

		double cost = coin.getQuoteForEuro() * buyInfo.getAmount();

		if(userEuroWalletEntry.getQuantity() <  cost) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enought money.");
		}

		//add the coin quantity to the user walletEntry for that coin and take the correspondant amount in euros 
		userWalletEntryForCoin.setQuantity(userWalletEntryForCoin.getQuantity() + buyInfo.getAmount());
		userEuroWalletEntry.setQuantity(userEuroWalletEntry.getQuantity() - cost);


		walletEntryService.update(userWalletEntryForCoin);
		walletEntryService.update(userEuroWalletEntry);

		// create the transaction
		transactionService.create(new Transaction( user, cost, 
				OperationType.BUY, userEuroWalletEntry.getCoin(), userWalletEntryForCoin.getCoin()) );

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/myApp/sell")
	public ResponseEntity<Void> sellCoin(@RequestHeader("Authorization") 
	String authorizationHeader, @RequestBody BuyOrSellRequestDTO sellInfo){

		//extract the token from the request header
		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		//get the user that is making the operation
		User user = tokenService.getUserByToken(token);

		Wallet userWallet = walletService.getWalletByUserId(user.getId());


		//get the wallet entry by the wallet id and coin name
		WalletEntry userWalletEntryForCoin =  walletEntryService.getByWalletIdAndCoinName(userWallet.getId(), sellInfo.getCoinName());

		WalletEntry userEuroWalletEntry =  walletEntryService.getEuroWalletEntriyByWalletId(userWallet.getId());

		Coin coin = coinService.getByName(sellInfo.getCoinName());

		double cost = coin.getQuoteForEuro() * sellInfo.getAmount();

		if(userWalletEntryForCoin.getQuantity() < sellInfo.getAmount()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enought amount to sell.");
		}

		//add the coin quantity to the user walletEntry for that coin and take the correspondant amount in euros 
		userWalletEntryForCoin.setQuantity(userWalletEntryForCoin.getQuantity() - sellInfo.getAmount());
		userEuroWalletEntry.setQuantity(userEuroWalletEntry.getQuantity() + cost);

		walletEntryService.update(userWalletEntryForCoin);
		walletEntryService.update(userEuroWalletEntry);

		// create the transaction
		
		transactionService.create( new Transaction( user, cost, 
				OperationType.SELL,userWalletEntryForCoin.getCoin(), userEuroWalletEntry.getCoin()));

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("myApp/wallet-entry/{coinId}")
	public ResponseEntity<WalletEntryResponse> getUserWalletEntriyByCoinId(@RequestHeader("Authorization") 
	String authorizationHeader, @PathVariable("coinId") int coinId) {
	
		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		//recover the user that is making the request using the token service
		User user = tokenService.getUserByToken(token);

		//get the wallet id of the user
		Wallet w = walletService.getWalletByUserId(user.getId());

		//find wallet entriy for the coin with the given id
		WalletEntry walletEntryForCoinId = walletEntryService.getWalletEntriyByWalletIdAndCoinId(w.getId(), coinId);

		//convert the walletEntry list to ists reponse type
		WalletEntryResponse walletEntriesResponse =new WalletEntryResponse(walletEntryForCoinId.getCoin(), walletEntryForCoinId.getQuantity());

		return new ResponseEntity<WalletEntryResponse>(walletEntriesResponse, HttpStatus.OK);	
	}
}
