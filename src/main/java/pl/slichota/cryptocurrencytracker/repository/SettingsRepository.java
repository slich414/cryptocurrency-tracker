package pl.slichota.cryptocurrencytracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.slichota.cryptocurrencytracker.domain.Settings;


@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Settings getSettingsByName(String name);

    String findByName(String name);


}
