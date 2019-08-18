package pl.krzyb.sweetdreamsbackend.cakestoppings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.krzyb.sweetdreamsbackend.cakes.Cake;
import pl.krzyb.sweetdreamsbackend.cakes.CakesRepository;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingNotFoundException;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CakesToppingsServiceTest {
    @Autowired
    private ToppingsRepository toppingsRepository;
    @Autowired
    private CakesRepository cakesRepository;
    @Autowired
    private CakesToppingsService service;

    @BeforeEach
    public void setUp() {
        List<Topping> toppings = List.of(new Topping("Whipped cream"), new Topping("Almonds"),
                new Topping("Poppy"), new Topping("Chocolate"));

        List<Cake> cakes = List.of(new Cake("Pie"), new Cake("Eclair"),
                new Cake("Cheese cake"), new Cake("Birthday cake"));

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
    public void getToppingsOfPieShouldReturnWhippedCream() {
        var result = service.getToppingsForCake("Pie");
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getName(), equalTo("Whipped cream"));
    }

    @Test
    public void addToppingToCakeShouldWork() {
        var result = service.addToppingToCake("Pie", "Chocolate");
        assertThat(result.getToppings().size(), equalTo(2));
        assertThat(result.getToppings().stream().anyMatch(
                topping -> topping.getName().equalsIgnoreCase("Chocolate")), is(true));
    }

    @Test
    public void deleteToppingShouldWork() {
        service.deleteToppingForCake("Pie", "Whipped cream");
        var result = service.getToppingsForCake("Pie");
        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void removingOfUnexistingConnectionShouldThrowException() {
        assertThrows(ToppingNotFoundException.class,
                () -> service.deleteToppingForCake("Pie", "unexisting"));
    }

    @Test
    public void removingOfUnconnectedToppingShouldThrowException() {
        assertThrows(CakeDoesNotHaveToppingException.class,
                () -> service.deleteToppingForCake("Pie", "Almonds"));
    }
}
