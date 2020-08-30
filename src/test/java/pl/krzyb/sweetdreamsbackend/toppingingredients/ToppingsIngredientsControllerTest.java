package pl.krzyb.sweetdreamsbackend.toppingingredients;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.krzyb.sweetdreamsbackend.ingredients.Ingredient;
import pl.krzyb.sweetdreamsbackend.ingredients.Taste;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppingsingredients.ToppingDoesNotHaveIngredientException;
import pl.krzyb.sweetdreamsbackend.toppingsingredients.ToppingsIngredientsService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ToppingsIngredientsControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ToppingsIngredientsService service;

    List<Ingredient> ingredients = List.of(new Ingredient("Sugar", Taste.SWEET),
            new Ingredient("Salt", Taste.SALTY), new Ingredient("Strawberry", Taste.SWEET),
            new Ingredient("Ginger", Taste.BITTER));


    @Test
    public void getIngredientsOfAlmondsShouldReturnSalt() throws Exception {
        //given
        when(service.getIngredientsForTopping("almonds")).thenReturn(ingredients);
        //when&then
        mvc.perform(get("/toppings/almonds/ingredients")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Sugar")));
    }

    @Test
    public void addIngredientToToppingShouldWork() throws Exception {
        //given
        Topping topping = new Topping("topping");
        topping.setIngredients(ingredients);
        when(service.addIngredientToTopping("topping", "ginger")).thenReturn(topping);
        //when&then
        mvc.perform(post("/toppings/topping/ingredients/ginger")).andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredients", hasSize(4)))
                .andExpect(jsonPath("$.ingredients[3].name", equalTo("Ginger")));
    }

    @Test
    public void deleteIngredientShouldWork() throws Exception {
        //when&then
        mvc.perform(delete("/toppings/almonds/ingredients/salt")).andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotExistingShouldThrowToppingDoesNotHaveIngredientException() throws Exception {
        //given
        doThrow(new ToppingDoesNotHaveIngredientException("pie", "poppy")).when(service).
                deleteIngredientOfTopping("pie", "poppy");
        //when&then
        mvc.perform(delete("/cakes/pie/toppings/poppy")).andExpect(status().isNotFound());
    }
}

