package pl.slichota.cryptocurrencytracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.slichota.cryptocurrencytracker.service.CryptocurrencyService;
import pl.slichota.cryptocurrencytracker.service.UserCompletedPurchaseService;

import java.io.IOException;

@SpringBootApplication
public class CryptocurrencyTrackerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CryptocurrencyTrackerApplication.class, args);

	}

	@Bean
	public CommandLineRunner setUpApp(@Autowired CryptocurrencyService cryptocurrencyService, UserCompletedPurchaseService userPurchaseService){
		return args -> {
			cryptocurrencyService.initialCryptocurrencies();;
		};
	}




}
