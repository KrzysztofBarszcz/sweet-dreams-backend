package pl.krzyb.sweetdreamsbackend.cakes;

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
public class CakesServiceTest {

    @MockBean
    private CakesRepository repository;
    @Autowired
    private CakesService service;

    Cake pie = new Cake("Pie", 10.12);
    Cake eclair = new Cake("Eclair", 33.44);
    List<Cake> cakes = List.of(pie, eclair, new Cake("Cheese cake", 6.79),
            new Cake("Birthday cake", 9.36));

    @Test
    public void getOneShouldReturnCake() {
        //given
        when(repository.findCakeByNameIgnoreCase("Pie")).thenReturn(pie);
        //when
        Cake result = service.getCake("Pie");
        //then
        assertThat(result.getName(), equalTo("Pie"));
    }

    @Test
    public void getAllShouldReturnAllCakes() {
        //given
        when(repository.findAll()).thenReturn(cakes);
        //when
        List<Cake> result = service.getCakes();
        //then
        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        //given
        when(repository.findCakeByNameIgnoreCase("EcLaIR")).thenReturn(eclair);
        //when
        Cake result = service.getCake("EcLaIR");
        //then
        assertThat(result.getName(), equalTo("Eclair"));
    }

    @Test
    public void getNotExistingCakeShouldThrowException() {
        //when&then
        Assertions.assertThrows(CakeNotFoundException.class,
                () -> service.getCake("not existing"));
    }

    @Test
    public void addCakeShouldWork() {
        //given
        Cake cake = new Cake("new cake", 1.0);
        when(repository.save(cake)).thenReturn(cake);
        //when
        var result = service.addCake(cake);
        //then
        assertEquals("Cake should be equal to the created", cake, result);
    }

    @Test
    public void removeCakeShouldWork() {
        //given
        when(repository.findCakeByNameIgnoreCase(pie.getName())).thenReturn(pie);
        //when
        var result = service.deleteCake(pie.getName());
        //then
        verify(repository).delete(pie);
        assertTrue("Cake was not removed", result);
    }

    @Test
    public void deleteNotExistingShouldThrowCakeNotFoundException() {
        //given
        when(repository.findCakeByNameIgnoreCase(eclair.getName())).thenReturn(null);
        //when
        var result = service.deleteCake(eclair.getName());
        //then
        assertFalse("Cake should not be found", result);
    }
}
