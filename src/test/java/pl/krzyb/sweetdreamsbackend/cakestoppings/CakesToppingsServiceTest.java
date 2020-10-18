package pl.krzyb.sweetdreamsbackend.cakestoppings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import pl.krzyb.sweetdreamsbackend.cakes.Cake;
import pl.krzyb.sweetdreamsbackend.cakes.CakesService;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingNotFoundException;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class CakesToppingsServiceTest {
    @MockBean
    private ToppingsService toppingsService;
    @MockBean
    private CakesService cakesService;
    @Autowired
    private CakesToppingsService service;

    Topping chocolate = new Topping("Chocolate");
    Topping whippedCream = new Topping("Whipped cream");
    List<Topping> toppings = List.of(whippedCream, new Topping("Almonds"),
            new Topping("Poppy"), chocolate);

    Cake pie = new Cake("Pie", 10.12);
    List<Cake> cakes = List.of(pie, new Cake("Eclair", 33.44),
            new Cake("Cheese cake", 6.79), new Cake("Birthday cake", 9.36));

    @BeforeEach
    public void setUp() {
        cakes.get(0).getToppings().add(toppings.get(0));
        cakes.get(1).getToppings().add(toppings.get(1));
        cakes.get(2).getToppings().add(toppings.get(2));
        cakes.get(3).getToppings().add(toppings.get(3));
    }

    @AfterEach
    public void tearDown() {
        for (Cake cake : cakes) {
            cake.setToppings(new ArrayList<>());
        }
    }

    @Test
    public void getToppingsOfPieShouldReturnWhippedCream() {
        //given
        when(cakesService.getCake("Pie")).thenReturn(pie);
        //when
        var result = service.getToppingsForCake("Pie");
        //then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getName(), equalTo("Whipped cream"));
    }

    @Test
    public void addToppingToCakeShouldWork() {
        //given
        when(cakesService.getCake("Pie")).thenReturn(pie);
        when(toppingsService.getTopping("Chocolate")).thenReturn(chocolate);
        when(cakesService.saveCake(pie)).thenReturn(pie);
        //when
        var result = service.addToppingToCake("Pie", "Chocolate");
        //then
        assertThat(result.getToppings().size(), equalTo(2));
        assertThat(result.getToppings().stream().anyMatch(
                topping -> topping.getName().equalsIgnoreCase("Chocolate")), is(true));
    }

    @Test
    public void deleteToppingShouldWork() {
        //given
        when(cakesService.getCake("Pie")).thenReturn(pie);
        when(toppingsService.getTopping("Whipped cream")).thenReturn(whippedCream);
        //when
        service.deleteToppingForCake("Pie", "Whipped cream");
        var result = pie.getToppings();
        //then
        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void removingOfNotExistingConnectionShouldThrowException() {
        //given
        when(cakesService.getCake("Pie")).thenReturn(pie);
        when(toppingsService.getTopping("unexisting")).thenThrow(new ToppingNotFoundException());
        //when&then
        assertThrows(ToppingNotFoundException.class,
                () -> service.deleteToppingForCake("Pie", "unexisting"));
    }

    @Test
    public void removingOfUnconnectedToppingShouldThrowException() {
        //given
        when(cakesService.getCake("Pie")).thenReturn(pie);
        when(toppingsService.getTopping("Chocolate")).thenReturn(chocolate);
        //when&then
        assertThrows(CakeDoesNotHaveToppingException.class,
                () -> service.deleteToppingForCake("Pie", "Chocolate"));
    }
}
