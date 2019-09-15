package pl.krzyb.sweetdreamsbackend.cakestoppings;

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
public class CakesToppingsControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ToppingsRepository toppingsRepository;
    @Autowired
    CakesRepository cakesRepository;

    @BeforeEach
    public void setUp() {
        List<Topping> toppings = List.of(new Topping("Whipped cream"), new Topping("Almonds"),
                new Topping("Poppy"), new Topping("Chocolate"));

        List<Cake> cakes = List.of(new Cake("Pie", 10.12), new Cake("Eclair", 33.44),
                new Cake("Cheese cake",6.79), new Cake("Birthday cake", 9.36));

        cakes.get(0).getToppings().add(toppings.get(0));
        cakes.get(1).getToppings().add(toppings.get(1));
        cakes.get(2).getToppings().add(toppings.get(2));
        cakes.get(3).getToppings().add(toppings.get(3));

        toppingsRepository.saveAll(toppings);
        cakesRepository.saveAll(cakes);
    }

    @AfterEach
    public void tearDown() {
        toppingsRepository.deleteAll();
        cakesRepository.deleteAll();
    }

    @Test
    public void getToppingsOfPieShouldReturnWhippedCream() throws Exception {
        mvc.perform(get("/cakes/pie/toppings")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Whipped cream")));
    }

    @Test
    public void addToppingToCakeShouldWork() throws Exception {
        mvc.perform(post("/cakes/pie/toppings/almonds")).andExpect(status().isOk())
                .andExpect(jsonPath("$.toppings", hasSize(2)))
                .andExpect(jsonPath("$.toppings[1].name", equalTo("Almonds")));
    }

    @Test
    public void deleteToppingShouldWork() throws Exception {
        mvc.perform(delete("/cakes/eclair/toppings/almonds")).andExpect(status().isNoContent());
    }
}
