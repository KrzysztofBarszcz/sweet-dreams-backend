package pl.krzyb.sweetdreamsbackend.ingredients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class IngredientsServiceTest {
    @Autowired
    private IngredientsService service;
    @MockBean
    private IngredientsRepository repository;

    private Ingredient salt = new Ingredient("Salt", Taste.SALTY);
    private Ingredient ginger = new Ingredient("Ginger", Taste.BITTER);

    List<Ingredient> ingredients = List.of(new Ingredient("Sugar", Taste.SWEET),
            salt, new Ingredient("Strawberry", Taste.SWEET),
            ginger);

    @Test
    public void getOneShouldReturnIngredient() {
        //given
        when(repository.findIngredientByNameIgnoreCase("Salt")).thenReturn(salt);
        //when
        Ingredient result = service.getIngredient("Salt");
        //then
        assertThat(result.getName(), equalTo("Salt"));
    }

    @Test
    public void getAllShouldReturnAllIngredients() {
        //given
        when(repository.findAll()).thenReturn(ingredients);
        //when
        List<Ingredient> result = service.getIngredients();
        //then
        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        //given
        when(repository.findIngredientByNameIgnoreCase("GinGEr")).thenReturn(ginger);
        //when
        Ingredient result = service.getIngredient("GinGEr");
        //then
        assertThat(result.getName(), equalTo("Ginger"));
    }

    @Test
    public void getNotExistingIngredientShouldThrowException() {
        //when&then
        Assertions.assertThrows(IngredientNotFoundException.class, () ->
                service.getIngredient("not existing"));
    }

    @Test
    public void addIngredientShouldWork() {
        //given
        Ingredient ingredient = new Ingredient("new ingredient", Taste.SWEET);
        when(repository.save(ingredient)).thenReturn(ingredient);
        //when
        var result = service.addIngredient(ingredient);
        //then
        assertEquals("Ingredient should be equal to the created", ingredient, result);
    }

    @Test
    public void removeIngredientShouldWork() {
        //given
        when(repository.findIngredientByNameIgnoreCase("Salt")).thenReturn(salt);
        //when
        service.deleteIngredient("Salt");
        //then
        verify(repository).delete(salt);
        assertThat(service.getIngredients().contains(salt), is(false));
    }
}
