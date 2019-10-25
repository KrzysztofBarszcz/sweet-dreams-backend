package pl.krzyb.sweetdreamsbackend.toppingsingredients;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzyb.sweetdreamsbackend.ingredients.Ingredient;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;

import java.util.List;

@RestController
@RequestMapping("toppings/{toppingName}/ingredients")
public class ToppingsIngredientsController {
    private final ToppingsIngredientsService service;

    public ToppingsIngredientsController(ToppingsIngredientsService service) {
        this.service = service;
    }

    @PostMapping("/{ingredientName}")
    public Topping addIngredientToTopping(@PathVariable String toppingName, @PathVariable String ingredientName) {
        return service.addIngredientToTopping(toppingName, ingredientName);
    }

    @GetMapping
    public List<Ingredient> getIngredients(@PathVariable String toppingName) {
        return service.getIngredientsForTopping(toppingName);
    }

    @DeleteMapping("/{ingredientName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredientOfTopping(@PathVariable String toppingName, @PathVariable String ingredientName) {
        service.deleteIngredientOfTopping(toppingName, ingredientName);
    }
}
