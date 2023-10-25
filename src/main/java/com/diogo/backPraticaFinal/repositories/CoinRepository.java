package com.diogo.backPraticaFinal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diogo.backPraticaFinal.models.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Integer>{

	public Optional<Coin> findByName(String coinName);

}