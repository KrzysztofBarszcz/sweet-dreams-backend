package pl.krzyb.sweetdreamsbackend.cakes;

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
public class CakesControllerIntegrationTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    private CakesRepository repository;

    @BeforeEach
    public void setUp() {
        List<Cake> cakes = List.of(new Cake("Pie", 10.12), new Cake("Eclair", 33.44),
                new Cake("Cheese cake",6.79), new Cake("Birthday cake", 9.36));
        repository.saveAll(cakes);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void getCakesShouldReturnOkAnd4Cakes() throws Exception {
        //when&then
        mvc.perform(get("/cakes")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getPieShouldReturnOk() throws Exception {
        //when&then
        mvc.perform(get("/cakes/pie")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Pie")));
    }

    @Test
    public void postNewCakeShouldReturnOkAndAddCake() throws Exception {
        //given
        ObjectMapper mapper = new ObjectMapper();
        Cake cake = new Cake("newcake", 6.83);
        String cakeString = mapper.writeValueAsString(cake);
        //when&then
        mvc.perform(post("/cakes")
                .contentType(MediaType.APPLICATION_JSON).content(cakeString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("newcake")));
    }

    @Test
    public void deleteCakeShouldRemoveIt() throws Exception {
        //given
        int cakesNumber = repository.findAll().size();
        //when&then
        mvc.perform(delete("/cakes/pie"))
                .andExpect(status().isNoContent());
        assertThat(repository.findAll().size(), equalTo(cakesNumber - 1));
    }
}
