package pl.krzyb.sweetdreamsbackend.toppings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ToppingsControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    private ToppingsRepository repository;

    @BeforeEach
    public void setUp() {
        List<Topping> toppings = List.of(new Topping("Whipped cream"), new Topping("Almonds"),
                new Topping("Poppy"), new Topping("Chocolate"));
        repository.saveAll(toppings);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void getCakesShouldReturnOkAnd4Cakes() throws Exception {
        mvc.perform(get("/toppings")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getAlmondsShouldReturnOk() throws Exception {
        mvc.perform(get("/toppings/almonds")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Almonds")));
    }

    @Test
    public void postNewToppingShouldReturnOkAndAddCake() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Topping topping = new Topping("newtopping");
        String cakeString = mapper.writeValueAsString(topping);
        mvc.perform(post("/toppings")
                .contentType(MediaType.APPLICATION_JSON).content(cakeString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("newtopping")));
    }

    @Test
    public void deleteToppingShouldRemoveIt() throws Exception {
        int toppingsNumber = repository.findAll().size();
        mvc.perform(delete("/toppings/almonds"))
                .andExpect(status().isNoContent());
        assertThat(repository.findAll().size(), equalTo(toppingsNumber - 1));
    }
}

