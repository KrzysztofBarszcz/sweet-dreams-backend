package pl.krzyb.sweetdreamsbackend.toppings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ToppingsControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    private ToppingsService service;

    Topping almonds = new Topping("Almonds");
    List<Topping> toppings = List.of(new Topping("Whipped cream"), almonds,
            new Topping("Poppy"), new Topping("Chocolate"));

    @Test
    public void getToppingsShouldReturnOkAnd4Toppings() throws Exception {
        //given
        when(service.getToppings()).thenReturn(toppings);
        //when&then
        mvc.perform(get("/toppings")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getAlmondsShouldReturnOk() throws Exception {
        //given
        when(service.getTopping("almonds")).thenReturn(almonds);
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
        when(service.addTopping(topping)).thenReturn(topping);
        //when&then
        mvc.perform(post("/toppings")
                .contentType(MediaType.APPLICATION_JSON).content(toppingString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("newtopping")));
    }

    @Test
    public void deleteToppingShouldRemoveIt() throws Exception {
        //given
        when(service.deleteTopping("almonds")).thenReturn(true);
        //when&then
        mvc.perform(delete("/toppings/almonds"))
                .andExpect(status().isNoContent());
        verify(service).deleteTopping("almonds");
    }
}
