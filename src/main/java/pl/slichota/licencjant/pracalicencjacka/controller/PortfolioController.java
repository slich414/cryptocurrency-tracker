package pl.slichota.licencjant.pracalicencjacka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.slichota.licencjant.pracalicencjacka.service.PortfolioService;
import pl.slichota.licencjant.pracalicencjacka.service.UserCompletedPurchaseService;

import java.io.IOException;

@Controller
public class PortfolioController {

    private final UserCompletedPurchaseService userFullPurchaseService;
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(UserCompletedPurchaseService userFullPurchaseService, PortfolioService portfolioService){
        this.userFullPurchaseService = userFullPurchaseService;
        this.portfolioService = portfolioService;
    }

    @GetMapping("/portfolio/history")
    public String historyPortfolio(Model model){

        model.addAttribute("userCryptocurrencies", userFullPurchaseService.getAllPurchases());
        model.addAttribute("unfinishedTransactions", portfolioService.convertToDTO());
        return "portfolio-history";
    }


    @GetMapping("/portfolio/manage")
    public String managePortfolio(Model model) throws IOException {
        model.addAttribute("actualPortfolio", portfolioService.getActualPortfolioBalance());
        return "portfolio-manage";
    }

}
