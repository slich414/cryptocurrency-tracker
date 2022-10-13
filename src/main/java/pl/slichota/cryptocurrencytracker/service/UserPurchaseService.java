package pl.slichota.cryptocurrencytracker.service;

import org.springframework.stereotype.Service;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserPurchase;
import pl.slichota.cryptocurrencytracker.exceptions.UserPurchaseNotFoundException;
import pl.slichota.cryptocurrencytracker.repository.UserPurchaseRepository;


import java.util.List;
import java.util.Optional;

@Service
public class UserPurchaseService {

    private final UserPurchaseRepository userPurchaseRepository;
    private final SettingsService settingsService;

    public UserPurchaseService(UserPurchaseRepository userPurchaseRepository, SettingsService settingsService){

        this.userPurchaseRepository = userPurchaseRepository;
        this.settingsService = settingsService;
    }

    public UserPurchase addUserPurchase(UserPurchase userPurchase){
        String currency = settingsService.getCurrency().getValue();
        userPurchase.setCurrency(currency);
        return userPurchaseRepository.save(userPurchase);
    }

    public List<UserPurchase> getAllUserPurchases(){

        return userPurchaseRepository.findAll();
    }
    public UserPurchase editUserPurchase(UserPurchase userPurchase, Long id){
        return userPurchaseRepository.findById(id)
                .map(foundUserBuy -> {
                            foundUserBuy.setCurrency(userPurchase.getCurrency());
                            foundUserBuy.setAmount(userPurchase.getAmount());
                            foundUserBuy.setCryptocurrency(userPurchase.getCryptocurrency());
                            foundUserBuy.setDescription(userPurchase.getDescription());
                            foundUserBuy.setTransactionPrice(userPurchase.getTransactionPrice());
                            foundUserBuy.setTransactionTime(userPurchase.getTransactionTime());

                            return userPurchaseRepository.save(foundUserBuy);
                        })
                .orElseThrow(() -> new UserPurchaseNotFoundException("UserPurchase with id " + id + " does not exists"));

    }

    public UserPurchase findUserPurchase(Long id) {
        Optional<UserPurchase> optionalUserBuy = userPurchaseRepository.findById(id);

        return optionalUserBuy.orElseGet(optionalUserBuy::get);

    }

    public void deleteUserPurchase(Long id){
        if(!userPurchaseRepository.existsById(id)){
            throw new UserPurchaseNotFoundException("UserPurchase with id " + id + " does not exists");
        }
        userPurchaseRepository.deleteById(id);
    }
}
