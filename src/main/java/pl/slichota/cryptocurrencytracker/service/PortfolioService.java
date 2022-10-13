package pl.slichota.cryptocurrencytracker.service;


import org.springframework.stereotype.Service;
import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserPurchase;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserSale;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserTransaction;
import pl.slichota.cryptocurrencytracker.domain.portfolio.dto.UserPortfolioBalanceDTO;
import pl.slichota.cryptocurrencytracker.domain.portfolio.dto.UserTransactionDTO;
import pl.slichota.cryptocurrencytracker.util.PriceCheckerUtil;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class PortfolioService {

    private final UserSaleService userSaleService;
    private final UserPurchaseService userPurchaseService;
    private final CryptocurrencyService cryptocurrencyService;
    private final SettingsService settingsService;

    public PortfolioService(UserSaleService userSaleService, UserPurchaseService userPurchaseService, CryptocurrencyService cryptocurrencyService, SettingsService settingsService) {
        this.userSaleService = userSaleService;
        this.userPurchaseService = userPurchaseService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.settingsService = settingsService;
    }


    public List<UserTransactionDTO> convertToDTO(){
        List<UserSale> userSales = userSaleService.getAllUserSales();
        List<UserPurchase> userPurchases = userPurchaseService.getAllUserPurchases();


        List<UserTransactionDTO> userTransactionDTOS = new ArrayList<>();
        for (UserSale userSale : userSales){
            userTransactionDTOS.add(new UserTransactionDTO(userSale.getId(), userSale.getAmount(), userSale.getTransactionPrice(),
                    userSale.getDescription(), userSale.getCurrency(), userSale.getCryptocurrency(), userSale.getTransactionTime(),
                    "sale"));
        }

        for(UserPurchase userPurchase : userPurchases){
            userTransactionDTOS.add(new UserTransactionDTO(userPurchase.getId(), userPurchase.getAmount(), userPurchase.getTransactionPrice(),
                    userPurchase.getDescription(), userPurchase.getCurrency(), userPurchase.getCryptocurrency(), userPurchase.getTransactionTime(),
                    "purchase"));
        }
        return userTransactionDTOS;
    }

    public List<UserPortfolioBalanceDTO> getActualPortfolioBalance() throws IOException {
        List<UserPortfolioBalanceDTO> userPortfolioBalanceDTOS = new ArrayList<>();
        List<Cryptocurrency> cryptocurrencies = cryptocurrencyService.allCryptocurrencies();
        for(Cryptocurrency cryptocurrency : cryptocurrencies){
            userPortfolioBalanceDTOS.add(new UserPortfolioBalanceDTO(cryptocurrency, 0.0, BigDecimal.valueOf(0)));
        }
        //get today day
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = dateTimeFormatter.format(localDateTime);


        List<UserTransaction> userTransactions = new ArrayList<>();
        userTransactions.addAll(userPurchaseService.getAllUserPurchases());
        userTransactions.addAll(userSaleService.getAllUserSales());

        for(UserTransaction userTransaction : userTransactions){
            if(userTransaction.getClass().getSimpleName().equals("UserPurchase")){
                for(UserPortfolioBalanceDTO userPortfolioBalanceDTO : userPortfolioBalanceDTOS){
                    if(userTransaction.getCryptocurrency().getName().equals(userPortfolioBalanceDTO.getCryptocurrency().getName())){
                        userPortfolioBalanceDTO.setAmount(userPortfolioBalanceDTO.getAmount() + userTransaction.getAmount());
                    }
                }
            }
            else{
                for(UserPortfolioBalanceDTO userPortfolioBalanceDTO : userPortfolioBalanceDTOS){
                    if(userTransaction.getCryptocurrency().getName().equals(userPortfolioBalanceDTO.getCryptocurrency().getName())){
                        userPortfolioBalanceDTO.setAmount(userPortfolioBalanceDTO.getAmount() - userTransaction.getAmount());                    }
                }
            }
        }
        
        for (UserPortfolioBalanceDTO userPortfolioBalanceDTO : userPortfolioBalanceDTOS){
            BigDecimal price = PriceCheckerUtil.getPrice(userPortfolioBalanceDTO.getCryptocurrency().getName().toLowerCase(), date, settingsService.getCurrency().getValue());
            BigDecimal value = price.multiply(BigDecimal.valueOf(userPortfolioBalanceDTO.getAmount()));
            BigDecimal result = userPortfolioBalanceDTO.getValue().add(value);
            userPortfolioBalanceDTO.setValue(BigDecimal.valueOf(result.doubleValue()));
        }
        return userPortfolioBalanceDTOS;
    }
}
