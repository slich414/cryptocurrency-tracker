package pl.slichota.cryptocurrencytracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserCompletedPurchase;
import pl.slichota.cryptocurrencytracker.service.CryptocurrencyService;
import pl.slichota.cryptocurrencytracker.service.PortfolioService;
import pl.slichota.cryptocurrencytracker.service.UserCompletedPurchaseService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserCompletedPurchaseController {
    private final CryptocurrencyService cryptocurrencyService;
    private final UserCompletedPurchaseService userCompletedPurchaseService;
    private final PortfolioService portfolioService;

    public UserCompletedPurchaseController(CryptocurrencyService cryptocurrencyService, UserCompletedPurchaseService userFullPurchaseService, PortfolioService portfolioService) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.userCompletedPurchaseService = userFullPurchaseService;
        this.portfolioService = portfolioService;
    }


    @GetMapping("/portfolio/add")
    public String addToPortfolio(Model model){

        model.addAttribute("purchaseToAdd", new UserCompletedPurchase());
        model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
        return "portfolio-add-fullpurchase";
    }

    @PostMapping("/portfolio/add")
    public String addToPortoflioSubmit(@Valid @ModelAttribute("purchaseToAdd") UserCompletedPurchase userFullPurchase, BindingResult bindingResult, Model model) throws IOException {

        if(bindingResult.hasErrors()){
            model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
            return "portfolio-add-fullpurchase";
        }

        userCompletedPurchaseService.addPurchase(userFullPurchase);
        return "redirect:/";
    }

    @GetMapping("/portfolio/edit/{id}")
    public String editFieldFromHistoryPortfolio(@PathVariable("id") Long id, Model model){

        model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
        model.addAttribute("userPurchaseToEdit", userCompletedPurchaseService.findUserPurchase(id));
        return "portfolio-edit-fullpurchase";
    }

    @PostMapping("/portfolio/edit/{id}")
    public String submitEditUserPurchase(@PathVariable("id") Long id, @Valid @ModelAttribute("userPurchaseToEdit") UserCompletedPurchase userFullPurchase, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
            return "portfolio-edit-fullpurchase";
        }
        userCompletedPurchaseService.editUserPurchase(userFullPurchase, id);
        model.addAttribute("userCryptocurrencies", userCompletedPurchaseService.getAllPurchases());
        model.addAttribute("unfinishedTransactions", portfolioService.convertToDTO());
        return "portfolio-history";
    }


    @GetMapping("/portfolio/delete/{id}")
    public String deleteUserPurchase(@PathVariable("id") Long id, Model model){
        userCompletedPurchaseService.deleteUserPurchase(id);
        model.addAttribute("userCryptocurrencies", userCompletedPurchaseService.getAllPurchases());
        model.addAttribute("unfinishedTransactions", portfolioService.convertToDTO());
        return "portfolio-history";
    }
}
