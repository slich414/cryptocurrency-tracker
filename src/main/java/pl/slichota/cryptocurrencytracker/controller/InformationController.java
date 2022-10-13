package pl.slichota.cryptocurrencytracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.slichota.cryptocurrencytracker.service.CryptocurrencyService;
import pl.slichota.cryptocurrencytracker.service.InformationService;

import java.io.IOException;

@Controller
@RequestMapping("info")
public class InformationController {

    private final InformationService informationService;
    private final CryptocurrencyService cryptocurrencyService;

    @Autowired
    public InformationController(InformationService informationService, CryptocurrencyService cryptocurrencyService){
        this.informationService = informationService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GetMapping
    public String getCrypcurrencyList(Model model){
        model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
        return "cryptocurrency-list";
    }

    @GetMapping("{name}")
    public String getInformation(@PathVariable("name") String name, Model model) throws IOException {
        model.addAttribute("dateMap", informationService.getLast12MonthsPrices(name));
        model.addAttribute("crypto", informationService.getCurrentPrice(name));
        return "cryptocurrency-information";
    }
}
