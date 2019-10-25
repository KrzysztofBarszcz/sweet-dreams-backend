package pl.krzyb.sweetdreamsbackend.toppingingredients;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.krzyb.sweetdreamsbackend.ingredients.Ingredient;
import pl.krzyb.sweetdreamsbackend.ingredients.IngredientNotFoundException;
import pl.krzyb.sweetdreamsbackend.ingredients.IngredientsRepository;
import pl.krzyb.sweetdreamsbackend.ingredients.Taste;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsRepository;
import pl.krzyb.sweetdreamsbackend.toppingsingredients.ToppingDoesNotHaveIngredientException;
import pl.krzyb.sweetdreamsbackend.toppingsingredients.ToppingsIngredientsService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ToppingsIngredientsServiceTest {
    @Autowired
    private ToppingsRepository toppingsRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private ToppingsIngredientsService service;

    @BeforeEach
    public void setUp() {
        List<Topping> toppings = List.of(new Topping("Whipped cream"), new Topping("Almonds"),
                new Topping("Poppy"), new Topping("Chocolate"));

        List<Ingredient> ingredients = List.of(new Ingredient("Sugar", Taste.SWEET),
                new Ingredient("Salt", Taste.SALTY), new Ingredient("Strawberry", Taste.SWEET),
                new Ingredient("Ginger", Taste.BITTER));

        toppings.get(0).getIngredients().add(ingredients.get(0));
        toppings.get(1).getIngredients().add(ingredients.get(1));
        toppings.get(2).getIngredients().add(ingredients.get(2));
        toppings.get(3).getIngredients().add(ingredients.get(3));

        toppingsRepository.saveAll(toppings);
        ingredientsRepository.saveAll(ingredients);
    }

    @AfterEach
    public void tearDown() {
        toppingsRepository.deleteAll();
        ingredientsRepository.deleteAll();
    }

    @Test
    public void getIngredientsOfAlmondsShouldReturnSalt() {
        var result = service.getIngredientsForTopping("Almonds");
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getName(), equalTo("Salt"));
    }

    @Test
    public void addIngredientToToppingShouldWork() {
        var result = service.addIngredientToTopping("Poppy", "Ginger");
        assertThat(result.getIngredients().size(), equalTo(2));
        assertThat(result.getIngredients().stream().anyMatch(
                ingredient -> ingredient.getName().equalsIgnoreCase("Ginger")), is(true));
    }

    @Test
    public void deleteToppingShouldWork() {
        service.deleteIngredientOfTopping("Poppy", "Strawberry");
        var result = service.getIngredientsForTopping("Poppy");
        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void removingOfUnexistingConnectionShouldThrowException() {
        assertThrows(IngredientNotFoundException.class,
                () -> service.deleteIngredientOfTopping("Poppy", "unexisting"));
    }

    @Test
    public void removingOfUnconnectedIngredientShouldThrowException() {
        assertThrows(ToppingDoesNotHaveIngredientException.class,
                () -> service.deleteIngredientOfTopping("Poppy", "Sugar"));
    }
}
