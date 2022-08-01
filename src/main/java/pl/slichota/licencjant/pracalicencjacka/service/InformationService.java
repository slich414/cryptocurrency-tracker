package pl.slichota.licencjant.pracalicencjacka.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.slichota.licencjant.pracalicencjacka.domain.Cryptocurrency;
import pl.slichota.licencjant.pracalicencjacka.util.PriceCheckerUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class InformationService {

    private final SettingsService settingsService;

    public InformationService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }


    public Map<String, BigDecimal> getLast12MonthsPrices(String name) throws IOException {
        String currency = settingsService.getCurrency().getValue();
        // get 12 months price
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Map<String, BigDecimal> dateMap = new LinkedHashMap<>();

        for(int i = 12; i>=0; i--){
            LocalDateTime localDateTime = LocalDateTime.now().minusMonths(i);
            String date = dateTimeFormatter.format(localDateTime);
            JSONObject jsonObject = PriceCheckerUtil.getJSONFromSelectedDate(name, date);
            BigDecimal price = PriceCheckerUtil.getPrice(jsonObject, currency).setScale(2, RoundingMode.HALF_UP);
            dateMap.put(dateTimeFormatter.format(localDateTime), price);
        }
        return dateMap;
    }

    public Cryptocurrency getCurrentPrice(String name) throws IOException {
        String currency = settingsService.getCurrency().getValue();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = dateTimeFormatter.format(localDateTime);
        // get current price
        JSONObject jsonObject = PriceCheckerUtil.getJSONFromSelectedDate(name, date);
        BigDecimal price = PriceCheckerUtil.getPrice(jsonObject, currency);
        BigDecimal totalValue = PriceCheckerUtil.getTotalVolume(jsonObject, currency);
        BigDecimal marketCap = PriceCheckerUtil.getMarketCap(jsonObject, currency);
        return new Cryptocurrency(name, price, currency, marketCap, totalValue);
    }
}
