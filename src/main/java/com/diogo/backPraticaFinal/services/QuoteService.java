package com.diogo.backPraticaFinal.services;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.diogo.backPraticaFinal.models.Coin;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuoteService {
	
	//##############################################################

	private final String API_KEY = "15325576-bd0d-43bb-8e9c-b1f0f6b2ee9a";

	private final String BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=4&convert=EUR";

	@Autowired
	private CoinService coinService;

	//##############################################################

	//the update of the quote of each coin will be made every 2 hours (7200000 ms) 
	@Scheduled(fixedRate = 7200000)
	public void updateCoinQuotesToEuro() {

		List<Coin> coins = coinService.findAll();

		RestTemplate restTemplate = new RestTemplate();

		// set the headers field for the http request including the api key
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-CMC_PRO_API_KEY", API_KEY); 

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		// make the http get request
		ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, requestEntity, String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		try {

			/*the hierarquy of nodes will be rootNode -> dataNode -> coinNode -> 
			 {symbolNode (to get the abbreviation of the coin)}
				-> eurNode -> priceNode
			 */

			//get the root node that holds other nodes
			JsonNode rootNode = objectMapper.readTree(response.getBody());

			//inside the root node exists the node called data which is the only we are interested in
			JsonNode dataNode = rootNode.get("data");

			//iterate each coin node inside the data node
			for (JsonNode coinNode : dataNode) {

				//coinSymbol is the same of the coin abbreviation
				String coinSymbol= coinNode.get("symbol").asText();

				JsonNode quoteNode = coinNode.get("quote");

				JsonNode eurNode = quoteNode.get("EUR");

				double coinQuoteForEuro  = Double.parseDouble(eurNode.get("price").asText());

				//get the coin and update its quote for euro
				for(Coin c : coins) {
					if(getAbbreviationForCoin(c.getName()).toLowerCase().equals(coinSymbol.toLowerCase())) {
						c.setQuoteForEuro(coinQuoteForEuro);
						coinService.update(c);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private String getAbbreviationForCoin(String coinName) {
		switch (coinName.toLowerCase()) {
		case "btc":
			return "BTC";
		case "ether":
			return "ETH";
		case "thehter":
			return "USDT";
		case "binacecoin":
			return "BNB";
		default:
			return "";
		}
	}


}
