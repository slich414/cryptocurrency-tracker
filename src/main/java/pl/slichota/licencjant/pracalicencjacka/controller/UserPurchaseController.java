package pl.slichota.licencjant.pracalicencjacka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.slichota.licencjant.pracalicencjacka.domain.portfolio.UserPurchase;
import pl.slichota.licencjant.pracalicencjacka.service.CryptocurrencyService;
import pl.slichota.licencjant.pracalicencjacka.service.PortfolioService;
import pl.slichota.licencjant.pracalicencjacka.service.UserCompletedPurchaseService;
import pl.slichota.licencjant.pracalicencjacka.service.UserPurchaseService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserPurchaseController {

    private final CryptocurrencyService cryptocurrencyService;
    private final UserPurchaseService userPurchaseService;
    private final UserCompletedPurchaseService userFullPurchaseService;
    private final PortfolioService portfolioService;

    public UserPurchaseController(CryptocurrencyService cryptocurrencyService, UserPurchaseService userPurchaseService, UserCompletedPurchaseService userFullPurchaseService, PortfolioService portfolioService) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.userPurchaseService = userPurchaseService;
        this.userFullPurchaseService = userFullPurchaseService;
        this.portfolioService = portfolioService;
    }


    @GetMapping("/portfolio/purchase/add")
    public String getNewPurchase(Model model){
        model.addAttribute("purchaseToAdd", new UserPurchase());
        model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
        return "portfolio-add-purchase";
    }


    @PostMapping("/portfolio/purchase/add")
    public String getNewBuySubmit(@Valid @ModelAttribute("purchaseToAdd") UserPurchase userPurchase, BindingResult bindingResult, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
            return "portfolio-add-purchase";
        }
        userPurchaseService.addUserPurchase(userPurchase);
        return "redirect:/";
    }

    @GetMapping("/portfolio/purchase/delete/{id}")
    public String deletePurchase(@PathVariable("id") Long id, Model model){
        userPurchaseService.deleteUserPurchase(id);
        model.addAttribute("userCryptocurrencies", userFullPurchaseService.getAllPurchases());
        model.addAttribute("unfinishedTransactions", portfolioService.convertToDTO());
        return "portfolio-history";
    }


    @GetMapping("/portfolio/purchase/edit/{id}")
    public String editPurchase(@PathVariable("id") Long id, Model model){
        model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
        model.addAttribute("userBuyToEdit", userPurchaseService.findUserPurchase(id));
        return "portfolio-edit-purchase";
    }

    @PostMapping("/portfolio/purchase/edit/{id}")
    public String editPurchaseSubmit(@PathVariable("id") Long id, @Valid @ModelAttribute("userBuyToEdit") UserPurchase userPurchase, BindingResult bindingResult,
                                     Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("cryptocurrencies", cryptocurrencyService.allCryptocurrencies());
            return "portfolio-edit-purchase";
        }
        userPurchaseService.editUserPurchase(userPurchase, id);
        model.addAttribute("userCryptocurrencies", userFullPurchaseService.getAllPurchases());
        model.addAttribute("unfinishedTransactions", portfolioService.convertToDTO());
        return "portfolio-history";
    }
}
