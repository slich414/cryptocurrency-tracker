package pl.slichota.cryptocurrencytracker.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;
import pl.slichota.cryptocurrencytracker.repository.CryptocurrencyRepository;

import java.util.List;

@Service
public class CryptocurrencyService {


    @Autowired
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final SettingsService settingsService;

    public CryptocurrencyService(CryptocurrencyRepository cryptocurrencyRepository, SettingsService settingsService){
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.settingsService = settingsService;
    }

    public Cryptocurrency addCryptocurrency(Cryptocurrency cryptocurrency){
        return cryptocurrencyRepository.save(cryptocurrency);
    }

    public List<Cryptocurrency> allCryptocurrencies(){
        return cryptocurrencyRepository.findAll();
    }

    public Cryptocurrency findCryptocurrency(Long id){
        return cryptocurrencyRepository.getById(id);
    }

    public void initialCryptocurrencies(){
        settingsService.addCurrency("usd");
        cryptocurrencyRepository.save(new Cryptocurrency("Bitcoin", "/logo/bitcoin-logo.jpg"));
        cryptocurrencyRepository.save(new Cryptocurrency("Ethereum", "/logo/etherneum-logo.png"));
        cryptocurrencyRepository.save(new Cryptocurrency("Litecoin", "/logo/litecoin-logo.png"));
        cryptocurrencyRepository.save(new Cryptocurrency("Ripple", "/logo/ripple-logo.jpg"));
        cryptocurrencyRepository.save(new Cryptocurrency("Bitcoin-cash", "/logo/bitcoin-cash-logo.png"));
        cryptocurrencyRepository.save(new Cryptocurrency("Filecoin", "/logo/filecoin-logo.png"));
        cryptocurrencyRepository.save(new Cryptocurrency("Cardano", "/logo/cardano-logo.jpg"));

    }


}
