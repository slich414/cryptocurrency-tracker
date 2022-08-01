package pl.slichota.licencjant.pracalicencjacka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.slichota.licencjant.pracalicencjacka.service.CryptocurrencyService;
import pl.slichota.licencjant.pracalicencjacka.service.UserCompletedPurchaseService;

import java.io.IOException;
import java.util.stream.IntStream;

@SpringBootApplication
public class PracaLicencjackaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PracaLicencjackaApplication.class, args);


	}

	@Bean
	public CommandLineRunner setUpApp(@Autowired CryptocurrencyService cryptocurrencyService, UserCompletedPurchaseService userPurchaseService){
		return args -> {
			cryptocurrencyService.initialCryptocurrencies();;
		};
	}




}
