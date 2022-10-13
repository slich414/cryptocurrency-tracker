package pl.slichota.cryptocurrencytracker.domain.portfolio.dto;


import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;

import java.math.BigDecimal;

public class UserPortfolioBalanceDTO {

    private Cryptocurrency cryptocurrency;
    private Double amount;
    private BigDecimal value;

    public UserPortfolioBalanceDTO(Cryptocurrency cryptocurrency, Double amount, BigDecimal value) {
        this.cryptocurrency = cryptocurrency;
        this.amount = amount;
        this.value = value;
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
