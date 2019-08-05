package pl.krzyb.sweetdreamsbackend.cakes;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CakesServiceTest {

    private CakesService service = new CakesService();

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
}
