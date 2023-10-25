package com.diogo.backPraticaFinal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diogo.backPraticaFinal.models.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

	public Optional<Wallet>findByUserId(Integer userId);
}
