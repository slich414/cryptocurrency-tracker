package pl.slichota.cryptocurrencytracker.domain.portfolio;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;
import pl.slichota.cryptocurrencytracker.validator.CompareDatesConstraint;
import pl.slichota.cryptocurrencytracker.validator.DateTimeConstraint;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@CompareDatesConstraint
public class UserCompletedPurchase extends UserTransaction{

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateTimeConstraint
    private Date soldTime;

    @Column(nullable = false)
    @NotNull(message = "Sold Price cannot have null value")
    private BigDecimal soldPrice;

    private BigDecimal profit = null;

    public UserCompletedPurchase() {
    }


    public UserCompletedPurchase(Double amount, BigDecimal purchasePrice, BigDecimal soldPrice,
                                 String description, String currency, Cryptocurrency cryptocurrency,
                                 @NonNull Date boughtTime, @NonNull Date soldTime) {

        super(amount, purchasePrice, description, currency, cryptocurrency, boughtTime);
        this.soldPrice = soldPrice;
        this.soldTime = soldTime;
    }
    public UserCompletedPurchase(Double amount, BigDecimal purchasePrice, BigDecimal soldPrice, BigDecimal profit,
                                 String description, String currency, Cryptocurrency cryptocurrency,
                                 @NonNull Date boughtTime, @NonNull Date soldTime) {

        super(amount, purchasePrice, description, currency, cryptocurrency, boughtTime);
        this.soldPrice = soldPrice;
        this.profit = profit;
        this.soldTime = soldTime;
    }

    public Date getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(Date soldTime) {
        this.soldTime = soldTime;
    }


    public BigDecimal getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(BigDecimal soldPrice) {
        this.soldPrice = soldPrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

}
