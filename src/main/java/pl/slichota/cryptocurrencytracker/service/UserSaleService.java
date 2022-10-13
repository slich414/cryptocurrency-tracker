package pl.slichota.cryptocurrencytracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserSale;
import pl.slichota.cryptocurrencytracker.exceptions.UserSaleNotFoundException;
import pl.slichota.cryptocurrencytracker.repository.UserSaleRepository;


import java.util.List;
import java.util.Optional;

@Service
public class UserSaleService {


    private final UserSaleRepository userSaleRepository;
    private final SettingsService settingsService;

    @Autowired
    public UserSaleService(UserSaleRepository userSaleRepository, SettingsService settingsService) {
        this.userSaleRepository = userSaleRepository;
        this.settingsService = settingsService;
    }


    public UserSale editUserSale(UserSale userSale, Long id){
        return userSaleRepository.findById(id)
                .map(foundUserBuy -> {
                    foundUserBuy.setCurrency(userSale.getCurrency());
                    foundUserBuy.setAmount(userSale.getAmount());
                    foundUserBuy.setCryptocurrency(userSale.getCryptocurrency());
                    foundUserBuy.setDescription(userSale.getDescription());
                    foundUserBuy.setTransactionPrice(userSale.getTransactionPrice());
                    foundUserBuy.setTransactionTime(userSale.getTransactionTime());

                    return userSaleRepository.save(foundUserBuy);
                })
                .orElseThrow(() -> new UserSaleNotFoundException("UserSale with id " + id + " does not exists"));

    }

    public UserSale findUserSale(Long id) {
        Optional<UserSale> optionalUserSale = userSaleRepository.findById(id);
        return optionalUserSale.get();
    }
    public List<UserSale> getAllUserSales(){

        return userSaleRepository.findAll();
    }

    public void addUserSale(UserSale userSale){
        String currency = settingsService.getCurrency().getValue();
        userSale.setCurrency(currency);
        userSaleRepository.save(userSale);
    }
    public void deleteUserSale(Long id){
        if(!userSaleRepository.existsById(id)){
            throw new UserSaleNotFoundException("UserSale with id " + id + " does not exists");
        }
        userSaleRepository.deleteById(id);
    }
}
