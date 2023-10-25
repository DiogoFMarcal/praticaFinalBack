package com.diogo.backPraticaFinal.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diogo.backPraticaFinal.models.OperationType;
import com.diogo.backPraticaFinal.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByUserId(Integer userId);
	
	@Query("select t from Transaction t where t.user.id = :userId and (:startSearchDate is null or :startSearchDate <= t.date)"
			+ " and (:endSearchDate is null or :endSearchDate >= t.date) "
			+ "and (:sourceCoinName is null or :sourceCoinName = t.sourceCoin.name) "
			+ "and (:destinationCoinName is null or :destinationCoinName = t.destinationCoin.name) "
			+ "and (:operationType is null or :operationType = t.operationType)")
	List<Transaction> findByUserIdWithFilters(Integer userId, Date startSearchDate, Date endSearchDate, String sourceCoinName, 
			String destinationCoinName, OperationType operationType );

}
