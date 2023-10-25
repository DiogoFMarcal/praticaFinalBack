package com.diogo.backPraticaFinal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diogo.backPraticaFinal.models.WalletEntry;
@Repository
public interface WalletEntryRepository extends JpaRepository<WalletEntry, Integer>{

	List<WalletEntry> findByWalletId(Integer walletId);

	@Query("select w from WalletEntry w where w.coin.name ='Euro' and w.wallet.id = ?1 ")
	Optional<WalletEntry> findEuroWalletEntryByUserId(Integer walletId);

	@Query("select w from WalletEntry w where w.wallet.id = ?1 and w.coin.name =?2")
	Optional<WalletEntry> findByWalletIdAndCoinName(Integer walletId, String coinName);

	@Query("select w from WalletEntry w where w.wallet.id = ?1 and w.coin.id =?2")
	Optional<WalletEntry> findByWalletIdAndCoinId(Integer walletId, Integer coinId);

}