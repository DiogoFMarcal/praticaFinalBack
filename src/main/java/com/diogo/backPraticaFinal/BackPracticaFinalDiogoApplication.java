package com.diogo.backPraticaFinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.diogo.backPraticaFinal.models.Coin;
import com.diogo.backPraticaFinal.models.User;
import com.diogo.backPraticaFinal.services.CoinService;
import com.diogo.backPraticaFinal.services.QuoteService;
import com.diogo.backPraticaFinal.services.UserService;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
public class BackPracticaFinalDiogoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackPracticaFinalDiogoApplication.class, args);
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@Autowired
	private QuoteService quoteService;

	@Override
	public void run(ApplicationArguments args) throws Exception { 
		
		//create the coins
		coinService.create(new Coin("Euro", 1 ));
		coinService.create(new Coin("Btc", 26844.52 ));
		coinService.create(new Coin("Ether", 1675.99 ));
		coinService.create(new Coin("Thehter", 1675.99 ));
		coinService.create(new Coin("BinaceCoin", 1675.99 ));
		
		User user = new User(1, "Joao", "Silva", "oioioiO#", "joao@email.com", "Portugal", "Lisboa", "Rua das Flores, 123", "PT1111111111111111111111");
		
		User user1 = new User(2, "Jose", "Ferreira", "olaJose#", "jose@email.com", "Portugal", "Lisboa", "Rua das Flores, 123", "PT1111111111111111111112");
 
		userService.createUser(user);
		userService.createUser(user1);
		
		//get the current quotes for euro to the coins of the systemm
		quoteService.updateCoinQuotesToEuro();
		
	}

}
