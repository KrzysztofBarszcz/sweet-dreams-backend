package pl.krzyb.sweetdreamsbackend.toppings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
        Optional<Topping> result = service.getTopping("Almonds");
        assertThat(result.get().getName(), equalTo("Almonds"));
    }

    @Test
    public void getAllShouldReturnAllToppings() {
        List<Topping> result = service.getToppings();
        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        Optional<Topping> result = service.getTopping("PopPY");
        assertThat(result.get().getName(), equalTo("Poppy"));
    }

    @Test
    public void getNotExistingToppingShouldEmptyOptional() {
        Optional<Topping> result = service.getTopping("not existing");
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void addToppingShouldWork() {
        Topping topping = new Topping("new topping");
        service.addTopping(topping);
        assertThat(service.getToppings().contains(topping), is(true));
    }

    @Test
    public void removeCakeShouldWork() {
        Topping cakeToRemove = new Topping("pie");
        service.deleteTopping(cakeToRemove.getName());
        assertThat(service.getToppings().contains(cakeToRemove), is(false));
    }
}
