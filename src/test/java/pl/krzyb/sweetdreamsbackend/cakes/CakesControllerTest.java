package pl.krzyb.sweetdreamsbackend.cakes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CakesControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void getCakesShouldReturnOkAnd4Cakes() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cakes")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getPieShouldReturnOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cakes/pie")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Pie")));
    }
}
