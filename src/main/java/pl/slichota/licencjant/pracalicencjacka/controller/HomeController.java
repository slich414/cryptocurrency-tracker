package pl.slichota.licencjant.pracalicencjacka.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.slichota.licencjant.pracalicencjacka.domain.Settings;
import pl.slichota.licencjant.pracalicencjacka.service.SettingsService;

@Controller
@RequestMapping("/")
public class HomeController {

    private final SettingsService settingsService;

    @Autowired
    public HomeController(SettingsService settingsService){
        this.settingsService = settingsService;
    }

    @GetMapping
    public String getIndex(){

        return "index";
    }

    @GetMapping("/settings")
    public String getSettings(){
        return "settings";
    }

    @GetMapping("/settings/currency")
    public String getChosenSetting(@RequestParam("currency") String currency){

        Settings currencyVar = settingsService.getCurrency();
        if(currencyVar == null){
            settingsService.addCurrency(currency);
        }
        else{
            currencyVar.setValue(currency);
            settingsService.updateCurrency(currencyVar);
        }
        return "redirect:/";
    }

}
