package com.diogo.backPraticaFinal.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diogo.backPraticaFinal.models.OperationType;

@RestController
public class OperationTypeController {

	@GetMapping("/myApp/operation-type")
	public ResponseEntity<List<String>> getAllOperationTypes(){

		//get the label of each operation type into an ArrayList
		List<String> operationTypesLabels = new ArrayList<>();
		OperationType[] operationTypes = OperationType.values();

		for (OperationType operationType : operationTypes) {
			operationTypesLabels.add(operationType.getOperationTypeString());
		}

		return new ResponseEntity<List<String>>(operationTypesLabels, HttpStatus.OK);
	}
}
