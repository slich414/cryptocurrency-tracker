package Services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.slichota.licencjant.pracalicencjacka.domain.Cryptocurrency;
import pl.slichota.licencjant.pracalicencjacka.domain.Settings;
import pl.slichota.licencjant.pracalicencjacka.domain.portfolio.UserCompletedPurchase;
import pl.slichota.licencjant.pracalicencjacka.domain.portfolio.UserSale;
import pl.slichota.licencjant.pracalicencjacka.exceptions.UserCompletedPurchaseNotFoundException;
import pl.slichota.licencjant.pracalicencjacka.exceptions.UserPurchaseNotFoundException;
import pl.slichota.licencjant.pracalicencjacka.repository.UserCompletedPurchaseRepository;
import pl.slichota.licencjant.pracalicencjacka.service.SettingsService;
import pl.slichota.licencjant.pracalicencjacka.service.UserCompletedPurchaseService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserCompletedPurchaseServiceTest {

    @Mock
    private SettingsService settingsService;

    @Mock
    private UserCompletedPurchaseRepository userCompletedPurchaseRepository;

    private UserCompletedPurchaseService underTest;

    @BeforeEach
    void setUp(){
        underTest = new UserCompletedPurchaseService(userCompletedPurchaseRepository, settingsService);
    }


    @Test
    void canGetAllUserCompletedPurchases(){
        // when
        underTest.getAllPurchases();

        // then
        verify(userCompletedPurchaseRepository).findAll();
    }

    @Test
    void canAddUserCompletedPurchases() throws IOException {
        //given
       UserCompletedPurchase userCompletedPurchase = new UserCompletedPurchase(1.0, BigDecimal.valueOf(1.0),BigDecimal.valueOf(1.0),
               "test", "pln", new Cryptocurrency("bitcoin"), Calendar.getInstance().getTime(), Calendar.getInstance().getTime());

        given(settingsService.getCurrency()).willReturn(new Settings("currency", "usd"));


        //when
        underTest.addPurchase(userCompletedPurchase);

        //then
        ArgumentCaptor<UserCompletedPurchase> userCompletedPurchaseArgumentCaptor = ArgumentCaptor.forClass(UserCompletedPurchase.class);
        verify(userCompletedPurchaseRepository).save(userCompletedPurchaseArgumentCaptor.capture());
        UserCompletedPurchase capturedUserCompletedPurchase = userCompletedPurchaseArgumentCaptor.getValue();
        assertThat(capturedUserCompletedPurchase).isEqualTo(userCompletedPurchase);
    }

    @Test
    void canDeleteUserCompletedPurchase(){
        //given
        long id=10;
        given(userCompletedPurchaseRepository.existsById(id)).willReturn(true);

        //when
        underTest.deleteUserPurchase(id);

        //then
        verify(userCompletedPurchaseRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteUserCompletedPurchaseNotFound(){
        // given
        long id = 10;
        given(userCompletedPurchaseRepository.existsById(id)).willReturn(false);

        // when
        assertThatThrownBy(() -> underTest.deleteUserPurchase(id))
                .isInstanceOf(UserCompletedPurchaseNotFoundException.class)
                .hasMessageContaining("UserCompletedPurchase with id " + id + " does not exists");

        // then
        verify(userCompletedPurchaseRepository, never()).deleteById(any());
    }
}
