package com.diogo.backPraticaFinal.dtos.requestsDTOs;

public class TakeRequestDTO {
	
	private String destinationIban;
	
	private double takeValue;

	public TakeRequestDTO(String destinationIban, double takeValue) {
		this.destinationIban = destinationIban;
		this.takeValue = takeValue;
	}

	public TakeRequestDTO() {}

	public String getDestinationIban() {
		return destinationIban;
	}

	public void setDestinationIban(String destinationIban) {
		this.destinationIban = destinationIban;
	}

	public double getTakeValue() {
		return takeValue;
	}

	public void setTakeValue(double takeValue) {
		this.takeValue = takeValue;
	}
}
