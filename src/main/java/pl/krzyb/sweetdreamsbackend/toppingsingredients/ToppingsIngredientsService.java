package pl.krzyb.sweetdreamsbackend.toppingsingredients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.krzyb.sweetdreamsbackend.ingredients.Ingredient;
import pl.krzyb.sweetdreamsbackend.ingredients.IngredientsService;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsService;

import java.util.List;

@Service
@Slf4j
public class ToppingsIngredientsService {

    private final ToppingsService toppingsService;
    private final IngredientsService ingredientsService;

    public ToppingsIngredientsService(ToppingsService toppingsService, IngredientsService ingredientsService) {
        this.toppingsService = toppingsService;
        this.ingredientsService = ingredientsService;
    }

    public Topping addIngredientToTopping(String toppingName, String ingredientName) {
        Ingredient ingredient = ingredientsService.getIngredient(ingredientName);
        Topping topping = toppingsService.getTopping(toppingName);

        topping.getIngredients().add(ingredient);
        log.debug("Added new ingredient: {}, to topping: {}", ingredient.getName(), topping.getName());
        return toppingsService.saveTopping(topping);
    }

    public List<Ingredient> getIngredientsForTopping(String toppingName) {
        Topping topping = toppingsService.getTopping(toppingName);

        log.debug("Found {} toppings", topping.getIngredients().size());
        return topping.getIngredients();
    }


    public void deleteIngredientOfTopping(String toppingName, String ingredientName) {
        Topping topping = toppingsService.getTopping(toppingName);
        Ingredient ingredient = ingredientsService.getIngredient(ingredientName);
        var ingredients = topping.getIngredients();
        if (!ingredients.contains(ingredient))
            throw new ToppingDoesNotHaveIngredientException(toppingName, ingredientName);
        ingredients.remove(ingredient);

        toppingsService.saveTopping(topping);
    }
}
