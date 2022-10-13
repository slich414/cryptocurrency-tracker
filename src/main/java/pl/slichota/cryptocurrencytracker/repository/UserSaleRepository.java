package pl.slichota.cryptocurrencytracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserSale;

@Repository
public interface UserSaleRepository extends JpaRepository<UserSale, Long> {
}
