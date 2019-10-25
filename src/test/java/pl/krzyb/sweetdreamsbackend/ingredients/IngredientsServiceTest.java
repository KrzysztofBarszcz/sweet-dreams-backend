package pl.krzyb.sweetdreamsbackend.ingredients;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class IngredientsServiceTest {
    @Autowired
    private IngredientsRepository repository;
    @Autowired
    private IngredientsService service;

    @BeforeEach
    public void setUp() {
        List<Ingredient> ingredients = List.of(new Ingredient("Sugar", Taste.SWEET),
                new Ingredient("Salt", Taste.SALTY), new Ingredient("Strawberry", Taste.SWEET),
                new Ingredient("Ginger", Taste.BITTER));
        repository.saveAll(ingredients);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void getOneShouldReturnIngredient() {
        Ingredient result = service.getIngredient("Salt");
        assertThat(result.getName(), equalTo("Salt"));
    }

    @Test
    public void getAllShouldReturnAllIngredients() {
        List<Ingredient> result = service.getIngredients();
        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        Ingredient result = service.getIngredient("GinGEr");
        assertThat(result.getName(), equalTo("Ginger"));
    }

    @Test
    public void getNotExistingIngredientShouldThrowException() {
        Assertions.assertThrows(IngredientNotFoundException.class, () ->
                service.getIngredient("not existing"));
    }

    @Test
    public void addIngredientShouldWork() {
        Ingredient ingredient = new Ingredient("new ingredient", Taste.SWEET);
        service.addIngredient(ingredient);
        assertThat(service.getIngredients().stream().anyMatch(
                (item) -> item.getName().equalsIgnoreCase(ingredient.getName())), is(true));
    }

    @Test
    public void removeIngredientShouldWork() {
        Ingredient ingredientToRemove = new Ingredient("Salt", Taste.SALTY);
        service.deleteIngredient(ingredientToRemove.getName());
        assertThat(service.getIngredients().contains(ingredientToRemove), is(false));
    }
}
