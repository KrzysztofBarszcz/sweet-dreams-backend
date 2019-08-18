package pl.krzyb.sweetdreamsbackend.cakestoppings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CakeDoesNotHaveToppingException extends RuntimeException {
    public CakeDoesNotHaveToppingException(String cake, String topping) {
        super(String.format("Cake %s does not have this topping: %s", cake, topping));
    }
}
