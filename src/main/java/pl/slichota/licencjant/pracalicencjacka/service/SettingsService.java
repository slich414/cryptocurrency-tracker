package pl.slichota.licencjant.pracalicencjacka.service;


import org.springframework.stereotype.Service;
import pl.slichota.licencjant.pracalicencjacka.domain.Settings;
import pl.slichota.licencjant.pracalicencjacka.repository.SettingsRepository;

@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository){
        this.settingsRepository = settingsRepository;
    }
    public String checkIfCurrencyIsInRepository(){
        return settingsRepository.findByName("currency");
    }
    public Settings addCurrency(String currency){
        return settingsRepository.save(new Settings("currency", currency));
    }
    public Settings getCurrency(){
        return settingsRepository.getSettingsByName("currency");
    }
    public Settings updateCurrency(Settings settings){
        return settingsRepository.save(settings);
    }


}
