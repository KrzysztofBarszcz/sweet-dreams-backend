package pl.krzyb.sweetdreamsbackend.cakes;


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
public class CakesControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    private CakesService service;

    Cake pie = new Cake("Pie", 10.12);

    List<Cake> cakes = List.of(pie, new Cake("Eclair", 33.44),
            new Cake("Cheese cake", 6.79), new Cake("Birthday cake", 9.36));

    @Test
    public void getCakesShouldReturnOkAnd4Cakes() throws Exception {
        //given
        when(service.getCakes()).thenReturn(cakes);
        //when&then
        mvc.perform(get("/cakes")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getPieShouldReturnOk() throws Exception {
        //given
        when(service.getCake("pie")).thenReturn(pie);
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

        when(service.addCake(cake)).thenReturn(cake);
        //when&then
        mvc.perform(post("/cakes")
                .contentType(MediaType.APPLICATION_JSON).content(cakeString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("newcake")));
    }

    @Test
    public void deleteCakeShouldRemoveIt() throws Exception {
        //given
        when(service.deleteCake("pie")).thenReturn(true);
        //when&then
        mvc.perform(delete("/cakes/pie"))
                .andExpect(status().isNoContent());
        verify(service).deleteCake("pie");
    }
}
