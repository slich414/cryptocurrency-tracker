package pl.slichota.licencjant.pracalicencjacka.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserPurchaseNotFoundException extends RuntimeException {

    public UserPurchaseNotFoundException(String msg){
        super(msg);
    }
}
