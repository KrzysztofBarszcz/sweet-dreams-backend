package pl.krzyb.sweetdreamsbackend.cakes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    @BeforeEach
    public void setUp() {
        CakesMock.refreshCakes();
    }

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

    @Test
    public void postNewCakeShouldReturnOkAndAddCake() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Cake cake = new Cake("newcake");
        String cakeString = mapper.writeValueAsString(cake);
        mvc.perform(MockMvcRequestBuilders.post("/cakes").
                contentType(MediaType.APPLICATION_JSON).content(cakeString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("newcake")));
    }

    @Test
    public void deleteCakeShouldRemoveIt() throws Exception {
        int cakesNumber = CakesMock.CAKES.size();
        mvc.perform(MockMvcRequestBuilders.delete("/cakes/pie"))
                .andExpect(status().isNoContent());
        MatcherAssert.assertThat(CakesMock.CAKES.size(), equalTo(cakesNumber - 1));
    }
}
