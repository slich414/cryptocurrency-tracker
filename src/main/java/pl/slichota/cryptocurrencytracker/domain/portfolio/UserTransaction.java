package pl.slichota.licencjant.pracalicencjacka.domain.portfolio;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import pl.slichota.licencjant.pracalicencjacka.domain.Cryptocurrency;
import pl.slichota.licencjant.pracalicencjacka.validator.DateTimeConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@MappedSuperclass
public abstract class UserTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @NotNull(message = "Amount cannot have null value")
    private Double amount;

    @Column(nullable = false)
    @NotNull(message = "Transaction Price cannot have null value")
    private BigDecimal transactionPrice;

    protected String description;

    private String currency;

    @OneToOne
    private Cryptocurrency cryptocurrency;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateTimeConstraint
    private Date transactionTime;

    public UserTransaction(){}

    public UserTransaction(Double amount, BigDecimal transactionPrice, String description, String currency, Cryptocurrency cryptocurrency, @NonNull Date transactionTime) {
        this.amount = amount;
        this.transactionPrice = transactionPrice;
        this.description = description;
        this.currency = currency;
        this.cryptocurrency = cryptocurrency;
        this.transactionTime = transactionTime;
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

    @NonNull
    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(@NonNull Date transactionTime) {
        this.transactionTime = transactionTime;
    }
}
