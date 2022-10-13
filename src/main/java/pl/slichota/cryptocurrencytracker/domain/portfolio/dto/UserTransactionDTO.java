package pl.slichota.cryptocurrencytracker.domain.portfolio.dto;


import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;

import java.math.BigDecimal;
import java.util.Date;

public class UserTransactionDTO {
    private Long id;
    private Double amount;
    private BigDecimal transactionPrice;
    private String description;
    private String currency;
    private Cryptocurrency cryptocurrency;
    private Date transactionTime;
    private String type;

    public UserTransactionDTO(Long id, Double amount, BigDecimal transactionPrice, String description, String currency, Cryptocurrency cryptocurrency, Date transactionTime, String type) {
        this.id = id;
        this.amount = amount;
        this.transactionPrice = transactionPrice;
        this.description = description;
        this.currency = currency;
        this.cryptocurrency = cryptocurrency;
        this.transactionTime = transactionTime;
        this.type = type;
    }

    public UserTransactionDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
