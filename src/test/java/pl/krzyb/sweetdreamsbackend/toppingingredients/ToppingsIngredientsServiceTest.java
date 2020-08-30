package pl.krzyb.sweetdreamsbackend.toppingingredients;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import pl.krzyb.sweetdreamsbackend.ingredients.Ingredient;
import pl.krzyb.sweetdreamsbackend.ingredients.IngredientNotFoundException;
import pl.krzyb.sweetdreamsbackend.ingredients.IngredientsService;
import pl.krzyb.sweetdreamsbackend.ingredients.Taste;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsService;
import pl.krzyb.sweetdreamsbackend.toppingsingredients.ToppingDoesNotHaveIngredientException;
import pl.krzyb.sweetdreamsbackend.toppingsingredients.ToppingsIngredientsService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class ToppingsIngredientsServiceTest {
    @MockBean
    private ToppingsService toppingsService;
    @MockBean
    private IngredientsService ingredientsService;
    @Autowired
    private ToppingsIngredientsService service;

    Topping almonds = new Topping("Almonds");
    List<Topping> toppings = List.of(almonds, new Topping("Whipped cream"),
            new Topping("Poppy"), new Topping("Chocolate"));

    Ingredient sugar = new Ingredient("Sugar", Taste.SWEET);
    Ingredient salt = new Ingredient("Salt", Taste.SALTY);
    List<Ingredient> ingredients = List.of(sugar, salt,
            new Ingredient("Strawberry", Taste.SWEET), new Ingredient("Ginger", Taste.BITTER));

    @BeforeEach
    public void setUp() {
        toppings.get(0).getIngredients().add(ingredients.get(0));
        toppings.get(1).getIngredients().add(ingredients.get(1));
        toppings.get(2).getIngredients().add(ingredients.get(2));
        toppings.get(3).getIngredients().add(ingredients.get(3));
    }

    @AfterEach
    public void tearDown() {
        for (Topping topping : toppings) {
            topping.setIngredients(new ArrayList<>());
        }
    }

    @Test
    public void getIngredientsOfAlmondsShouldReturnSalt() {
        //given
        when(toppingsService.getTopping("Almonds")).thenReturn(almonds);
        //when
        var result = service.getIngredientsForTopping("Almonds");
        //then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getName(), equalTo("Sugar"));
    }

    @Test
    public void addIngredientToToppingShouldWork() {
        //given
        when(toppingsService.getTopping("Almonds")).thenReturn(almonds);
        when(ingredientsService.getIngredient("Sugar")).thenReturn(sugar);
        when(toppingsService.saveTopping(almonds)).thenReturn(almonds);
        //when
        var result = service.addIngredientToTopping("Almonds", "Sugar");
        //then
        assertThat(result.getIngredients().size(), equalTo(2));
        assertThat(result.getIngredients().stream().anyMatch(
                ingredient -> ingredient.getName().equalsIgnoreCase("Sugar")), is(true));
    }

    @Test
    public void deleteToppingShouldWork() {
        //given
        when(toppingsService.getTopping("Almonds")).thenReturn(almonds);
        when(ingredientsService.getIngredient("Sugar")).thenReturn(sugar);
        //when
        service.deleteIngredientOfTopping("Almonds", "Sugar");
        var result = service.getIngredientsForTopping("Almonds");
        //then
        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void removingOfNotExistingConnectionShouldThrowException() {
        //given
        when(toppingsService.getTopping("Almonds")).thenReturn(almonds);
        when(ingredientsService.getIngredient("non-existing")).thenThrow(new IngredientNotFoundException());
        //when&then
        assertThrows(IngredientNotFoundException.class,
                () -> service.deleteIngredientOfTopping("Almonds", "non-existing"));
    }

    @Test
    public void removingOfUnconnectedIngredientShouldThrowException() {
        //given
        when(toppingsService.getTopping("Almonds")).thenReturn(almonds);
        when(ingredientsService.getIngredient("Salt")).thenReturn(salt);
        //when&then
        assertThrows(ToppingDoesNotHaveIngredientException.class,
                () -> service.deleteIngredientOfTopping("Almonds", "Salt"));
    }
}
