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
public class ToppingsControllerIntegrationTests {
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
    public void getToppingsShouldReturnOkAnd4Toppings() throws Exception {
        //when&then
        mvc.perform(get("/toppings")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getAlmondsShouldReturnOk() throws Exception {
        //when&then
        mvc.perform(get("/toppings/almonds")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Almonds")));
    }

    @Test
    public void postNewToppingShouldReturnOkAndAddTopping() throws Exception {
        //given
        ObjectMapper mapper = new ObjectMapper();
        Topping topping = new Topping("newtopping");
        String toppingString = mapper.writeValueAsString(topping);
        //when&then
        mvc.perform(post("/toppings")
                .contentType(MediaType.APPLICATION_JSON).content(toppingString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("newtopping")));
    }

    @Test
    public void deleteToppingShouldRemoveIt() throws Exception {
        //given
        int toppingsNumber = repository.findAll().size();
        //when&then
        mvc.perform(delete("/toppings/almonds"))
                .andExpect(status().isNoContent());
        assertThat(repository.findAll().size(), equalTo(toppingsNumber - 1));
    }
}
