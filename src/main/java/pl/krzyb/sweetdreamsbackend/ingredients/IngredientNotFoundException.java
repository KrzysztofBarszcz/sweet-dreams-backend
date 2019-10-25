package pl.krzyb.sweetdreamsbackend.ingredients;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException() {
        super("Ingredient with the given name does not exist");
    }
}
