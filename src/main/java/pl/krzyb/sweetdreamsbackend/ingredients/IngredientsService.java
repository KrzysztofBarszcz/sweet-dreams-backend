package pl.krzyb.sweetdreamsbackend.ingredients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IngredientsService {

    private final IngredientsRepository repository;

    IngredientsService(IngredientsRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getIngredients() {
        var ingredients = repository.findAll();
        log.debug("Found {} toppings.", ingredients.size());
        return ingredients;
    }

    public Ingredient getIngredient(String name) {
        var ingredient = Optional.ofNullable(repository.findIngredientByNameIgnoreCase(name));
        if(ingredient.isPresent())
            log.debug("Ingredient {} found.", name);
        return ingredient.orElseThrow(() -> {
            log.debug("Ingredient {} not found.", name);
            throw new IngredientNotFoundException();
        });
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        var savedIngredient = repository.save(ingredient);
        log.debug("Added new ingredient: {}.", savedIngredient.getName());
        return savedIngredient;
    }

    public boolean deleteIngredient(String name) {
        Ingredient ingredientToRemove = repository.findIngredientByNameIgnoreCase(name);
        if (ingredientToRemove != null) {
            repository.delete(ingredientToRemove);
            log.debug("Ingredient {} removed", name);
            return true;
        } else {
            log.debug("Tried to remove ingredient: {} but it does not exist.", name);
            return false;
        }
    }
}
