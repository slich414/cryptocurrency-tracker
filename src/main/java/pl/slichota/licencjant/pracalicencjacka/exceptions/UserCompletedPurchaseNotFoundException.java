package pl.slichota.licencjant.pracalicencjacka.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserCompletedPurchaseNotFoundException extends RuntimeException{

    public UserCompletedPurchaseNotFoundException(String msg){
        super(msg);
    }
}
