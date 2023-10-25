package com.diogo.backPraticaFinal.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.diogo.backPraticaFinal.dtos.transaction.TransactionResponseDTO;
import com.diogo.backPraticaFinal.models.OperationType;
import com.diogo.backPraticaFinal.models.Transaction;
import com.diogo.backPraticaFinal.models.User;
import com.diogo.backPraticaFinal.services.TokenService;
import com.diogo.backPraticaFinal.services.TransactionService;

@RestController
public class TransactionController {

	@Autowired 
	private TokenService tokenService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/myApp/transactions")
	public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(value = "startSearchDate", required = false) Date startSearchDate, 
			@RequestParam(value = "endSearchDate", required = false) Date endSearchDate,
			@RequestParam(value = "sourceCoinName", required = false) String sourceCoinName ,
			@RequestParam(value = "destinationCoinName", required = false) String destinationCoinName, 
			@RequestParam(value = "operationTypeName", required = false) String operationTypeName ){

		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		User user = tokenService.getUserByToken(token);

		//get the enumeration element of the operation type by the name sent on the request
		OperationType operationTypeToSearch = OperationType.getFromLabel(operationTypeName);

		//List of transactions of the user making the request
		List<Transaction> userTransactions = transactionService.getAllByUserIdWithFilters(user.getId(), startSearchDate, 
				endSearchDate, sourceCoinName, destinationCoinName, operationTypeToSearch );
		List<TransactionResponseDTO> userTransactionsResponse = new ArrayList<>();

		for(Transaction t: userTransactions ) {
			userTransactionsResponse.add(new TransactionResponseDTO(t.getDate(), t.getAmountInEuros(), 
					t.getOperationType().getOperationTypeString(), t.getSourceCoin().getName(), t.getDestinationCoin().getName()));
		}

		return new ResponseEntity<List<TransactionResponseDTO>>( userTransactionsResponse, HttpStatus.OK);
	}
}