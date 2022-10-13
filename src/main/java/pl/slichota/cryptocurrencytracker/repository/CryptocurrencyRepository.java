package pl.slichota.cryptocurrencytracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;


@Repository
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {

}
