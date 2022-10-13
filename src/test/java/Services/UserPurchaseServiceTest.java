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
import pl.slichota.cryptocurrencytracker.domain.portfolio.UserPurchase;
import pl.slichota.cryptocurrencytracker.exceptions.UserPurchaseNotFoundException;
import pl.slichota.cryptocurrencytracker.repository.UserPurchaseRepository;
import pl.slichota.cryptocurrencytracker.service.SettingsService;
import pl.slichota.cryptocurrencytracker.service.UserPurchaseService;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserPurchaseServiceTest {


    @Mock
    private UserPurchaseRepository userPurchaseRepository;

    @Mock
    private SettingsService settingsService;
    private UserPurchaseService underTest;

    @BeforeEach
    void setUp(){

        underTest = new UserPurchaseService(userPurchaseRepository, settingsService);
    }

    @Test
    void canGetAllUserPurchases(){
        // when
        underTest.getAllUserPurchases();

        // then
        verify(userPurchaseRepository).findAll();
    }

    @Test
    void canAddUserPurchase(){
        //given
        UserPurchase userPurchase = new UserPurchase(1.0, BigDecimal.valueOf(10000), "description", "usd"
        , new Cryptocurrency("bitcoin"), Calendar.getInstance().getTime());
        given(settingsService.getCurrency()).willReturn(new Settings("currency", "usd"));


        //when
        underTest.addUserPurchase(userPurchase);

        //then
        ArgumentCaptor<UserPurchase> userPurchaseArgumentCaptor = ArgumentCaptor.forClass(UserPurchase.class);
        verify(userPurchaseRepository).save(userPurchaseArgumentCaptor.capture());
        UserPurchase capturedUserPurchase = userPurchaseArgumentCaptor.getValue();
        assertThat(capturedUserPurchase).isEqualTo(userPurchase);

    }

    @Test
    void canDeleteUserPurchase(){
        //given
        long id=10;
        given(userPurchaseRepository.existsById(id)).willReturn(true);

        //when
        underTest.deleteUserPurchase(id);

        //then
        verify(userPurchaseRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteUserPurchaseNotFound(){
        // given
        long id = 10;
        given(userPurchaseRepository.existsById(id)).willReturn(false);

        // when
        assertThatThrownBy(() -> underTest.deleteUserPurchase(id))
                .isInstanceOf(UserPurchaseNotFoundException.class)
                .hasMessageContaining("UserPurchase with id " + id + " does not exists");

        // then
        verify(userPurchaseRepository, never()).deleteById(any());
    }
}
