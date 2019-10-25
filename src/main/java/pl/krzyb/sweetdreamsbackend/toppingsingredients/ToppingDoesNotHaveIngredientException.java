package pl.krzyb.sweetdreamsbackend.toppingsingredients;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToppingDoesNotHaveIngredientException extends RuntimeException {
    public ToppingDoesNotHaveIngredientException(String topping, String ingredient) {
        super(String.format("Topping %s does not have this ingredient: %s", topping, ingredient));
    }
}
