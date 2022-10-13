package pl.slichota.cryptocurrencytracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserPurchaseNotFoundException extends RuntimeException {

    public UserPurchaseNotFoundException(String msg){
        super(msg);
    }
}
