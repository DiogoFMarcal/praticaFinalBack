package com.diogo.backPraticaFinal.models;

public enum OperationType {
	SELL("Sell"), BUY("Buy"), DEPOSIT("Deposit"),TAKE("Take");

	private final String operationTypeString;

	OperationType(String operationTypeString) {
		this.operationTypeString = operationTypeString;
	}

	public String getOperationTypeString() {
		return operationTypeString;
	}

	//returns the enum element by its label/name
	public static OperationType getFromLabel(String operationTypeLabel) {
		for (OperationType operationType : values()) {
			if (operationType.operationTypeString.equals(operationTypeLabel)) {
				return operationType;
			}
		}
		return null;
	}
}
