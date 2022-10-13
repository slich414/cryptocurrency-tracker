package pl.slichota.cryptocurrencytracker.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;
    private String currency;
    // kapitalizacja rynkowa (ilosc kryptowalut * cena)
    private BigDecimal marketCap;
    //Wolumen obrotów – łączna liczba papierów wartościowych, które zmieniły właściciela, liczona dla danego papieru wartościowego lub rynku.
    private BigDecimal totalVolume;

    private String url;


    public Cryptocurrency(String name) {
        this.name = name;
    }


    public Cryptocurrency() {
    }

    public Cryptocurrency(String name, BigDecimal price, String currency, BigDecimal marketCap, BigDecimal totalVolume) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.marketCap = marketCap;
        this.totalVolume = totalVolume;
    }

    public Cryptocurrency(String name, BigDecimal price, String currency, BigDecimal marketCap, BigDecimal totalVolume, String url) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.marketCap = marketCap;
        this.totalVolume = totalVolume;
        this.url = url;
    }

    public Cryptocurrency(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
