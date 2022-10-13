package Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.slichota.cryptocurrencytracker.domain.Cryptocurrency;
import pl.slichota.cryptocurrencytracker.domain.Settings;
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserSale;
import pl.slichota.cryptocurrencytracker.exceptions.UserSaleNotFoundException;
import pl.slichota.cryptocurrencytracker.repository.UserSaleRepository;
import pl.slichota.cryptocurrencytracker.service.SettingsService;
import pl.slichota.cryptocurrencytracker.service.UserSaleService;


import java.math.BigDecimal;
import java.util.Calendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserSaleServiceTest {

    @Mock
    private UserSaleRepository userSaleRepository;

    @Mock
    private SettingsService settingsService;
    private UserSaleService underTest;

    @BeforeEach
    void setUp(){

        underTest = new UserSaleService(userSaleRepository, settingsService);
    }

    @Test
    void canGetAllUserSales(){
        // when
        underTest.getAllUserSales();

        // then
        verify(userSaleRepository).findAll();
    }

    @Test
    void canAddUserSale(){
        //given
        UserSale userSale = new UserSale(1.0, BigDecimal.valueOf(10000), "description", "usd"
                , new Cryptocurrency("bitcoin"), Calendar.getInstance().getTime());
        given(settingsService.getCurrency()).willReturn(new Settings("currency", "usd"));


        //when
        underTest.addUserSale(userSale);

        //then
        ArgumentCaptor<UserSale> userSaleArgumentCaptor = ArgumentCaptor.forClass(UserSale.class);
        verify(userSaleRepository).save(userSaleArgumentCaptor.capture());
        UserSale capturedUserSale = userSaleArgumentCaptor.getValue();
        assertThat(capturedUserSale).isEqualTo(userSale);

    }

    @Test
    void canDeleteUserSale(){
        //given
        long id=10;
        given(userSaleRepository.existsById(id)).willReturn(true);

        //when
        underTest.deleteUserSale(id);

        //then
        verify(userSaleRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteUserSaleNotFound(){
        // given
        long id = 10;
        given(userSaleRepository.existsById(id)).willReturn(false);

        // when
        assertThatThrownBy(() -> underTest.deleteUserSale(id))
                .isInstanceOf(UserSaleNotFoundException.class)
                .hasMessageContaining("UserSale with id " + id + " does not exists");

        // then
        verify(userSaleRepository, never()).deleteById(any());
    }

}
