package pl.krzyb.sweetdreamsbackend.toppings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class ToppingsServiceTest {

    @Autowired
    private ToppingsService service;
    @MockBean
    private ToppingsRepository repository;

    Topping almonds = new Topping("Almonds");
    Topping poppy = new Topping("Poppy");
    List<Topping> toppings = List.of(new Topping("Whipped cream"), almonds,
            new Topping("Poppy"), new Topping("Chocolate"));

    @Test
    public void getOneShouldReturnTopping() {
        //given
        when(repository.findToppingByNameIgnoreCase("Almonds")).thenReturn(almonds);
        //when
        Topping result = service.getTopping("Almonds");
        //then
        assertThat(result.getName(), equalTo("Almonds"));
    }

    @Test
    public void getAllShouldReturnAllToppings() {
        //given
        when(repository.findAll()).thenReturn(toppings);
        //when
        List<Topping> result = service.getToppings();
        //then
        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        //given
        when(repository.findToppingByNameIgnoreCase("PopPY")).thenReturn(poppy);
        //when
        Topping result = service.getTopping("PopPY");
        //then
        assertThat(result.getName(), equalTo("Poppy"));
    }

    @Test
    public void getNotExistingToppingShouldEmptyOptional() {
        Assertions.assertThrows(ToppingNotFoundException.class, () ->
                service.getTopping("not existing"));
    }

    @Test
    public void addToppingShouldWork() {
        //given
        Topping topping = new Topping("new topping");
        when(repository.save(topping)).thenReturn(topping);
        var result = service.addTopping(topping);
        //when&then
        assertEquals("Topping should be equal to the created", topping, result);
    }

    @Test
    public void removeToppingShouldWork() {
        //given
        when(repository.findToppingByNameIgnoreCase("poppy")).thenReturn(poppy);
        //when
        var result = service.deleteTopping("poppy");
        //then
        verify(repository).delete(poppy);
        assertTrue("Cake was not removed", result);
    }

    @Test
    public void deleteNotExistingShouldThrowToppingNotFoundException() {
        //given
        when(repository.findToppingByNameIgnoreCase(poppy.getName())).thenReturn(null);
        //when
        var result = service.deleteTopping(poppy.getName());
        //then
        assertFalse("Cake should not be found", result);
    }
}
