package pl.slichota.licencjant.pracalicencjacka.util;


import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PriceCheckerUtil {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject getJSONFromSelectedDate(String cryptocurrency, String date) throws IOException {

        String url = String.format("https://api.coingecko.com/api/v3/coins/%s/history?date=%s&localization=false", cryptocurrency, date);
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }

    }

    public static BigDecimal getPrice(JSONObject jsonObject, String currency){
        return ((BigDecimal) jsonObject.getJSONObject("market_data").getJSONObject("current_price").get(currency)).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getMarketCap(JSONObject jsonObject, String currency){
        return ((BigDecimal) jsonObject.getJSONObject("market_data").getJSONObject("market_cap").get(currency)).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getTotalVolume(JSONObject jsonObject, String currency){
        return ((BigDecimal) jsonObject.getJSONObject("market_data").getJSONObject("total_volume").get(currency)).setScale(2, RoundingMode.HALF_UP);
    }








}
