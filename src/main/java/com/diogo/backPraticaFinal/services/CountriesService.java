package com.diogo.backPraticaFinal.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.diogo.backPraticaFinal.dtos.country.CountryDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CountriesService {

	public boolean checkCountryExists( String countryName) {

		String baseUrl = "https://restcountries.com/v3.1/name/" + countryName + "?fields=name";

		RestTemplate restTemplate = new RestTemplate();

		try {
			restTemplate.getForEntity(baseUrl, Object[].class);
			return true;
		}catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				return false;
			}
		}
		return false;
	}

	public List<CountryDTO> getCountries() {
		String baseUrl = "https://restcountries.com/v3.1/all/?fields=name";
		RestTemplate restTemplate = new RestTemplate();

		// make the http request wich returns a  JSON
		String response = restTemplate.getForObject(baseUrl, String.class);

		// Usar a biblioteca Jackson para analisar a resposta JSON
		ObjectMapper objectMapper = new ObjectMapper();
		List<CountryDTO> countryNames = new ArrayList<>();

		try {
			JsonNode rootNode = objectMapper.readTree(response);

			if (rootNode.isArray()) {
				for (JsonNode countryNode : rootNode) {
					JsonNode nameNode = countryNode.get("name");
					if (nameNode != null && nameNode.has("common")) {
						countryNames.add(new CountryDTO(nameNode.get("common").asText()) );
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return countryNames;
	}
}