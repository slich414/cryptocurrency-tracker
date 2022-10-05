package pl.slichota.licencjant.pracalicencjacka.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.slichota.licencjant.pracalicencjacka.domain.portfolio.UserCompletedPurchase;

import pl.slichota.licencjant.pracalicencjacka.exceptions.UserCompletedPurchaseNotFoundException;
import pl.slichota.licencjant.pracalicencjacka.repository.UserCompletedPurchaseRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class UserCompletedPurchaseService {

    private final UserCompletedPurchaseRepository userCompletedPurchaseRepository;
    private final SettingsService settingsService;



    @Autowired
    public UserCompletedPurchaseService(UserCompletedPurchaseRepository userCompletedPurchaseRepository, SettingsService settingsService
    ){
        this.userCompletedPurchaseRepository = userCompletedPurchaseRepository;
        this.settingsService = settingsService;
    }


    public UserCompletedPurchase addPurchase(UserCompletedPurchase userFullPurchase) throws IOException {


        userFullPurchase.setProfit(countProfit(userFullPurchase));

        String currency = settingsService.getCurrency().getValue();
        userFullPurchase.setCurrency(currency);
        System.out.println("Z serwisu: " + userFullPurchase.getAmount());

        return userCompletedPurchaseRepository.save(userFullPurchase);
    }

    public List<UserCompletedPurchase> getAllPurchases(){

        return userCompletedPurchaseRepository.findAll();
    }


    public void deleteUserPurchase(Long id){
        boolean exists = userCompletedPurchaseRepository.existsById(id);
        if(exists){
            userCompletedPurchaseRepository.deleteById(id);
        }
        else{
            throw new UserCompletedPurchaseNotFoundException("UserCompletedPurchase with id " + id + " does not exists");
        }
    }


    public UserCompletedPurchase findUserPurchase(Long id){
        Optional<UserCompletedPurchase> userPurchase = userCompletedPurchaseRepository.findById(id);
        return userPurchase.orElseThrow(() -> new UserCompletedPurchaseNotFoundException("UserCompletedPurchase with id " + id + " does not exists"));
    }

    public UserCompletedPurchase editUserPurchase(UserCompletedPurchase userFullPurchase, Long id){
        return userCompletedPurchaseRepository.findById(id)
                .map(foundUserFullPurchase -> {
                    foundUserFullPurchase.setAmount(userFullPurchase.getAmount());
                    foundUserFullPurchase.setTransactionTime(userFullPurchase.getTransactionTime());
                    foundUserFullPurchase.setSoldTime(userFullPurchase.getSoldTime());
                    foundUserFullPurchase.setCryptocurrency(userFullPurchase.getCryptocurrency());
                    foundUserFullPurchase.setCurrency(userFullPurchase.getCurrency());
                    foundUserFullPurchase.setDescription(userFullPurchase.getDescription());
                    foundUserFullPurchase.setTransactionPrice(userFullPurchase.getTransactionPrice());
                    foundUserFullPurchase.setSoldPrice(userFullPurchase.getSoldPrice());
                    foundUserFullPurchase.setProfit(countProfit(userFullPurchase));



                    return userCompletedPurchaseRepository.save(foundUserFullPurchase);
                })
                .orElseThrow(() -> new UserCompletedPurchaseNotFoundException("UserCompletedPurchase with id " + id + " does not exists"));

    }

    private BigDecimal countProfit(UserCompletedPurchase userFullPurchase){
        //            Sprawdz czy obliczyc o ile obnizono czy uroslo
        BigDecimal profit;
        // sold - purchase > 0
        if (userFullPurchase.getSoldPrice().subtract(userFullPurchase.getTransactionPrice()).compareTo(BigDecimal.valueOf(0)) > 0) {
            profit = userFullPurchase.getSoldPrice().subtract(userFullPurchase.getTransactionPrice());
            profit = profit.divide(userFullPurchase.getTransactionPrice(),2, RoundingMode.HALF_UP);
            profit = profit.multiply(BigDecimal.valueOf(100));
        }
//            sold == purchase
        else if (userFullPurchase.getSoldPrice().subtract(userFullPurchase.getTransactionPrice()).compareTo(BigDecimal.valueOf(0)) == 0) {
            profit = userFullPurchase.getSoldPrice().subtract(userFullPurchase.getTransactionPrice());
            profit = profit.divide(userFullPurchase.getTransactionPrice(), 2, RoundingMode.HALF_UP);
            profit = profit.multiply(BigDecimal.valueOf(100));
        }
        else{
            profit = userFullPurchase.getTransactionPrice().subtract(userFullPurchase.getSoldPrice());
            profit = profit.divide(userFullPurchase.getSoldPrice(), 2, RoundingMode.HALF_UP);
            profit = profit.multiply(BigDecimal.valueOf(100));
            profit = profit.negate();
        }
        return profit;
    }

}
