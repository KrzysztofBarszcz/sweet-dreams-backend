package pl.krzyb.sweetdreamsbackend.ingredients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientsControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    private IngredientsRepository repository;

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
    public void getIngredientsShouldReturnOkAnd4Ingredients() throws Exception {
        mvc.perform(get("/ingredients")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getGingerShouldReturnOk() throws Exception {
        mvc.perform(get("/ingredients/ginger")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Ginger")));
    }

    @Test
    public void postNewIngredientShouldReturnOkAndAddIngredient() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Ingredient ingredient = new Ingredient("newingredient", Taste.SALTY);
        String ingredientString = mapper.writeValueAsString(ingredient);
        mvc.perform(post("/ingredients")
                .contentType(MediaType.APPLICATION_JSON).content(ingredientString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("newingredient")));
    }

    @Test
    public void deleteIngredientShouldRemoveIt() throws Exception {
        int ingredientsNumber = repository.findAll().size();
        mvc.perform(delete("/ingredients/strawberry"))
                .andExpect(status().isNoContent());
        MatcherAssert.assertThat(repository.findAll().size(), equalTo(ingredientsNumber - 1));
    }
}
