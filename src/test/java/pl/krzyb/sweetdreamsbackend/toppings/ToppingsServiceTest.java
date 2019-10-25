package pl.krzyb.sweetdreamsbackend.toppings;

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
public class ToppingsServiceTest {

    @Autowired
    private ToppingsRepository repository;
    @Autowired
    private ToppingsService service;

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
    public void getOneShouldReturnTopping() {
        Topping result = service.getTopping("Almonds");
        assertThat(result.getName(), equalTo("Almonds"));
    }

    @Test
    public void getAllShouldReturnAllToppings() {
        List<Topping> result = service.getToppings();
        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        Topping result = service.getTopping("PopPY");
        assertThat(result.getName(), equalTo("Poppy"));
    }

    @Test
    public void getNotExistingToppingShouldEmptyOptional() {
        Assertions.assertThrows(ToppingNotFoundException.class, () ->
                service.getTopping("not existing"));
    }

    @Test
    public void addToppingShouldWork() {
        Topping topping = new Topping("new topping");
        service.addTopping(topping);
        assertThat(service.getToppings().stream().anyMatch(
                (item)->item.getName().equalsIgnoreCase(topping.getName())), is(true));
    }

    @Test
    public void removeToppingShouldWork() {
        Topping toppingToRemove = new Topping("pie");
        service.deleteTopping(toppingToRemove.getName());
        assertThat(service.getToppings().contains(toppingToRemove), is(false));
    }
}
