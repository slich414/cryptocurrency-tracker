package pl.slichota.cryptocurrencytracker.domain.portfolio;



import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class UserSale extends UserTransaction{

    public UserSale(Double amount, BigDecimal transactionPrice, String description, String currency, Cryptocurrency cryptocurrency, Date transactionTime) {
        super(amount, transactionPrice, description, currency, cryptocurrency, transactionTime);
    }

    public UserSale() {}

}


