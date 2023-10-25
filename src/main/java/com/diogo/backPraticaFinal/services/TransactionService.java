package com.diogo.backPraticaFinal.services;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diogo.backPraticaFinal.models.OperationType;
import com.diogo.backPraticaFinal.models.Transaction;
import com.diogo.backPraticaFinal.repositories.TransactionRepository;

@Service
public class TransactionService {
	
	//#####################################################################################

	@Autowired
	private TransactionRepository transactionRepository;
	
	//#####################################################################################
	
	public Transaction create(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public List<Transaction> getAllByUserId(Integer userId) {
		return transactionRepository.findByUserId(userId);
	}

	public List<Transaction> getAllByUserIdWithFilters(Integer userId, Date startSearchDate, Date endSearchDate, 
			String sourceCoinName, String destinationCoinName, OperationType operationType) {
		List<Transaction> ltrs = transactionRepository.findByUserIdWithFilters(userId, startSearchDate, endSearchDate, sourceCoinName, 
				destinationCoinName, operationType);

		return ltrs;
	}
	
}
