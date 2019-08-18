package pl.krzyb.sweetdreamsbackend.cakes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CakesServiceTest {

    @Autowired
    private CakesRepository repository;
    @Autowired
    private CakesService service;

    @BeforeEach
    public void setUp() {
        List<Cake> cakes = List.of(new Cake("Pie"), new Cake("Eclair"),
                new Cake("Cheese cake"), new Cake("Birthday cake"));
        repository.saveAll(cakes);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void getOneShouldReturnCake() {
        Cake result = service.getCake("Pie");
        assertThat(result.getName(), equalTo("Pie"));
    }

    @Test
    public void getAllShouldReturnAllCakes() {
        List<Cake> result = service.getCakes();

        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        Cake result = service.getCake("EcLaIR");
        assertThat(result.getName(), equalTo("Eclair"));
    }

    @Test
    public void getNotExistingCakeShouldThrowException() {
        Assertions.assertThrows(CakeNotFoundException.class,
                ()-> service.getCake("not existing"));
    }

    @Test
    public void addCakeShouldWork() {
        Cake cake = new Cake("new cake");
        service.addCake(cake);
        assertThat(service.getCakes().stream().anyMatch(
                (item)->item.getName().equalsIgnoreCase(cake.getName())), is(true));
    }

    @Test
    public void removeCakeShouldWork() {
        Cake cakeToRemove = new Cake("pie");
        service.deleteCake(cakeToRemove.getName());
        assertThat(service.getCakes().contains(cakeToRemove), is(false));
    }
}
