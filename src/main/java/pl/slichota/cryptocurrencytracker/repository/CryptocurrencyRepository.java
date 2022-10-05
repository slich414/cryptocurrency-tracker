package pl.slichota.licencjant.pracalicencjacka.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.slichota.licencjant.pracalicencjacka.domain.Cryptocurrency;

@Repository
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {

}
