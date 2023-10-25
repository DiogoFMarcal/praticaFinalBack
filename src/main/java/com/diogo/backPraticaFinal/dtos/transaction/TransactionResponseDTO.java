package com.diogo.backPraticaFinal.dtos.transaction;

import java.sql.Date;

public class TransactionResponseDTO {
	
	//############################################################################################
	
	private Date date;
	
	private double amountInEuros;
	
	private String operationType;
	
	private String sourceCoin;
	
	private String destinationCoin;
	
	//############################################################################################

	public TransactionResponseDTO(Date date, double amountInEuros, String operationType, String sourceCoin,
			String destinationCoin) {
		this.date = date;
		this.amountInEuros = amountInEuros;
		this.operationType = operationType;
		this.sourceCoin = sourceCoin;
		this.destinationCoin = destinationCoin;
	}
	
	public TransactionResponseDTO() {}

	//############################################################################################
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmountInEuros() {
		return amountInEuros;
	}

	public void setAmountInEuros(double amountInEuros) {
		this.amountInEuros = amountInEuros;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getSourceCoin() {
		return sourceCoin;
	}

	public void setSourceCoin(String sourceCoin) {
		this.sourceCoin = sourceCoin;
	}

	public String getDestinationCoin() {
		return destinationCoin;
	}

	public void setDestinationCoin(String destinationCoin) {
		this.destinationCoin = destinationCoin;
	}
}
