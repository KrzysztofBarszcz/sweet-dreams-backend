package pl.krzyb.sweetdreamsbackend.cakes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CakesServiceTest {

    private CakesService service = new CakesService();

    @BeforeEach
    public void setUp() {
        CakesMock.refreshCakes();
    }

    @Test
    public void getOneShouldReturnCake() {
        Optional<Cake> result = service.getCake("Pie");
        assertThat(result.get().getName(), equalTo("Pie"));
    }

    @Test
    public void getAllShouldReturnAllCakes() {
        List<Cake> result = service.getCakes();

        assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        Optional<Cake> result = service.getCake("EcLaIR");
        assertThat(result.get().getName(), equalTo("Eclair"));
    }

    @Test
    public void getNotExistingCakeShouldEmptyOptional() {
        Optional<Cake> result = service.getCake("not existing");
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void addCakeShouldWork() {
        Cake cake = new Cake("new cake");
        service.addCake(cake);
        assertThat(service.getCakes().contains(cake), is(true));
    }

    @Test
    public void removeCakeShouldWork() {
        Cake cakeToRemove = new Cake("pie");
        service.deleteCake(cakeToRemove.getName());
        assertThat(service.getCakes().contains(cakeToRemove), is(false));
    }
}
