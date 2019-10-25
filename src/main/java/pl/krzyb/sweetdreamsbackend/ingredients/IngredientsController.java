package pl.krzyb.sweetdreamsbackend.ingredients;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ingredients")
public class IngredientsController {
    IngredientsService service;

    IngredientsController(IngredientsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ingredient> getIngredients() {
        return service.getIngredients();
    }

    @GetMapping("/{name}")
    public Ingredient getIngredient(@PathVariable String name) {
        return service.getIngredient(name);
    }

    @PostMapping
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return service.addIngredient(ingredient);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable String name) {
        boolean isRemoved = service.deleteIngredient(name);
        if (!isRemoved) throw new IngredientNotFoundException();
    }
}
