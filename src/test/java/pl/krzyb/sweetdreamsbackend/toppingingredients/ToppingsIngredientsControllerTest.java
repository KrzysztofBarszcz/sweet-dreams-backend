package pl.krzyb.sweetdreamsbackend.toppingingredients;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.krzyb.sweetdreamsbackend.cakes.Cake;
import pl.krzyb.sweetdreamsbackend.cakes.CakesRepository;
import pl.krzyb.sweetdreamsbackend.ingredients.Ingredient;
import pl.krzyb.sweetdreamsbackend.ingredients.IngredientsRepository;
import pl.krzyb.sweetdreamsbackend.ingredients.Taste;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsRepository;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ToppingsIngredientsControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ToppingsRepository toppingsRepository;
    @Autowired
    IngredientsRepository ingredientsRepository;

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
    public void getIngredientsOfAlmondsShouldReturnSalt() throws Exception {
        mvc.perform(get("/toppings/almonds/ingredients")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Salt")));
    }

    @Test
    public void addIngredientToToppingShouldWork() throws Exception {
        mvc.perform(post("/toppings/almonds/ingredients/ginger")).andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredients", hasSize(2)))
                .andExpect(jsonPath("$.ingredients[1].name", equalTo("Ginger")));
    }

    @Test
    public void deleteIngredientShouldWork() throws Exception {
        mvc.perform(delete("/toppings/almonds/ingredients/salt")).andExpect(status().isNoContent());
    }
}
