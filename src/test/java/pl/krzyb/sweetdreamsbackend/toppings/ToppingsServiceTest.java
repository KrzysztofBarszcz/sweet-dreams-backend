package pl.krzyb.sweetdreamsbackend.toppings;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ToppingsServiceTest {

    private ToppingsService service = new ToppingsService();

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
}
