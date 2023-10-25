package com.diogo.backPraticaFinal.dtos.requestsDTOs;

public class DepositRequestDTO {
	
	private String sourceIban;
	
	private double depositValue;

	public DepositRequestDTO(String sourceIban, double depositValue) {
		super();
		this.sourceIban = sourceIban;
		this.depositValue = depositValue;
	}

	public DepositRequestDTO() {}

	public String getSourceIban() {
		return sourceIban;
	}

	public void setSourceIban(String sourceIban) {
		this.sourceIban = sourceIban;
	}

	public double getDepositValue() {
		return depositValue;
	}

	public void setDepositValue(double depositValue) {
		this.depositValue = depositValue;
	}
}
