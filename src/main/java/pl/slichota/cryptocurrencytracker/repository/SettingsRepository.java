package pl.slichota.licencjant.pracalicencjacka.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.slichota.licencjant.pracalicencjacka.domain.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Settings getSettingsByName(String name);

    String findByName(String name);


}
