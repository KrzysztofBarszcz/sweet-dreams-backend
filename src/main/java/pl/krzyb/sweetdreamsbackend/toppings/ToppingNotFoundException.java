package pl.krzyb.sweetdreamsbackend.toppings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToppingNotFoundException extends RuntimeException {
    public ToppingNotFoundException() {
        super("Topping with the given name does not exist");
    }
}
