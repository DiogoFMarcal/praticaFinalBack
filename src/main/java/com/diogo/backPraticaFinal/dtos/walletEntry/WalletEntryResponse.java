package com.diogo.backPraticaFinal.dtos.walletEntry;

import com.diogo.backPraticaFinal.models.Coin;

public class WalletEntryResponse {

	private Coin coin;

	private double quantity;

	public WalletEntryResponse(Coin coin, double quantity) {
		this.coin = coin;
		this.quantity = quantity;
	}

	public Coin getCoin() {
		return coin;
	}

	public void setCoin(Coin coin) {
		this.coin = coin;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}	
}
