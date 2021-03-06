package pl.krzyb.sweetdreamsbackend.cakestoppings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.krzyb.sweetdreamsbackend.cakes.Cake;
import pl.krzyb.sweetdreamsbackend.cakes.CakesRepository;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsRepository;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CakesToppingsControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    private CakesToppingsService service;

    List<Topping> toppings = List.of(new Topping("Whipped cream"), new Topping("Almonds"),
            new Topping("Poppy"), new Topping("Chocolate"));

    @Test
    public void getToppingsOfPieShouldReturnWhippedCream() throws Exception {
        //given
        when(service.getToppingsForCake("pie")).thenReturn(toppings);
        //when&then
        mvc.perform(get("/cakes/pie/toppings")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Whipped cream")));
    }

    @Test
    public void addToppingToCakeShouldWork() throws Exception {
        //given
        Cake cake = new Cake("cake", 12.0);
        cake.setToppings(toppings);
        when(service.addToppingToCake("pie", "chocolate")).thenReturn(cake);
        //when&then
        mvc.perform(post("/cakes/pie/toppings/chocolate")).andExpect(status().isOk())
                .andExpect(jsonPath("$.toppings", hasSize(4)))
                .andExpect(jsonPath("$.toppings[3].name", equalTo("Chocolate")));
        verify(service).addToppingToCake("pie", "chocolate");
    }

    @Test
    public void deleteToppingShouldWork() throws Exception {
        //when&then
        mvc.perform(delete("/cakes/eclair/toppings/almonds")).andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotExistingShouldThrowCakeDoesNotHaveToppingException() throws Exception {
        //given
        doThrow(new CakeDoesNotHaveToppingException("pie", "poppy"))
                .when(service).deleteToppingForCake("pie", "poppy");
        //when&then
        mvc.perform(delete("/cakes/pie/toppings/poppy")).andExpect(status().isNotFound());
    }
}
