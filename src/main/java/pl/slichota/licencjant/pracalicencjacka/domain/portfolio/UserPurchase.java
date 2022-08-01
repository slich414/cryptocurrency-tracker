package pl.slichota.licencjant.pracalicencjacka.domain.portfolio;

import pl.slichota.licencjant.pracalicencjacka.domain.Cryptocurrency;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class UserPurchase extends UserTransaction{


    public UserPurchase(Double amount, BigDecimal transactionPrice, String description, String currency, Cryptocurrency cryptocurrency, Date transactionTime) {
        super(amount, transactionPrice, description, currency, cryptocurrency, transactionTime);
    }

    public UserPurchase(){

    }

}
