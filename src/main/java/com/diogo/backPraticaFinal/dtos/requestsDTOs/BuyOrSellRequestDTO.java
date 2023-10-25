package com.diogo.backPraticaFinal.dtos.requestsDTOs;

public class BuyOrSellRequestDTO {

	private String coinName;
	
	private double amount;

	public BuyOrSellRequestDTO(String coinName, double amount) {
		this.coinName = coinName;
		this.amount = amount;
	}
	
	public BuyOrSellRequestDTO() {}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}	
}