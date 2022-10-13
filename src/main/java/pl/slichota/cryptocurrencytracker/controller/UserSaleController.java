package pl.slichota.cryptocurrencytracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserSale;
import pl.slichota.cryptocurrencytracker.service.*;

import javax.validation.Valid;

@Controller
public class UserSaleController {

    private final CryptocurrencyService cryptocurrencyService;
    private final SettingsService settingsService;
    private final UserSaleService userSaleService;
    private final UserCompletedPurchaseService userFullPurchaseService;
    private final PortfolioService portfolioService;

    public UserSaleController(CryptocurrencyService cryptocurrencyService, SettingsService settingsService, UserSaleService userSaleService, UserCompletedPurchaseService userFullPurchaseService, PortfolioService portfolioService) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.settingsService = settingsService;
        this.userSaleService = userSaleService;
        this.userFullPurchaseService = userFullPurchaseService;
        this.portfolioService = portfolioService;
    }


    @GetMapping("/portfolio/sale/add")
    public String getNewSale(Model model){
        model.addAttribute("saleToAdd", new UserSale());
        model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
        return "portfolio-add-sale";
    }
    @PostMapping("/portfolio/sale/add")
    public String getNewSaleSubmit(@Valid @ModelAttribute("saleToAdd") UserSale userSale, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
            return "portfolio-add-sale";
        }

        String currency = settingsService.getCurrency().getValue();
        userSale.setCurrency(currency);
        userSaleService.addUserSale(userSale);
        return "redirect:/";
    }


    @GetMapping("/portfolio/sale/delete/{id}")
    public String deleteSale(@PathVariable("id") Long id, Model model){
        userSaleService.deleteUserSale(id);
        model.addAttribute("userCryptocurrencies", userFullPurchaseService.getAllPurchases());
        model.addAttribute("unfinishedTransactions", portfolioService.convertToDTO());
        return "portfolio-history";
    }

    @GetMapping("/portfolio/sale/edit/{id}")
    public String editSale(@PathVariable("id") Long id, Model model){
        model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
        model.addAttribute("userSaleToEdit", userSaleService.findUserSale(id));
        return "portfolio-edit-sale";
    }

    @PostMapping("/portfolio/sale/edit/{id}")
    public String editSaleSubmit(@PathVariable("id") Long id, @Valid @ModelAttribute("userSaleToEdit") UserSale userSale, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
            return "portfolio-edit-sale";
        }
        userSaleService.editUserSale(userSale, id);
        model.addAttribute("userCryptocurrencies", userFullPurchaseService.getAllPurchases());
        model.addAttribute("unfinishedTransactions", portfolioService.convertToDTO());
        return "portfolio-history";
    }
}
