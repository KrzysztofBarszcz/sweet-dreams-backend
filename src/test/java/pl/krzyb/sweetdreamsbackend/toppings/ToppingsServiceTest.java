package pl.krzyb.sweetdreamsbackend.toppings;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class ToppingsServiceTest {

    private ToppingsService service = new ToppingsService();

    @Test
    public void getOneShouldReturnTopping() {
        Optional<Topping> result = service.getTopping("Almonds");
        Assert.assertThat(result.get().getName(), equalTo("Almonds"));
    }

    @Test
    public void getAllShouldReturnAllToppings() {
        List<Topping> result = service.getToppings();
        Assert.assertThat(result, hasSize(4));
    }

    @Test
    public void getShouldIgnoreStringCase() {
        Optional<Topping> result = service.getTopping("PopPY");
        Assert.assertThat(result.get().getName(), equalTo("Poppy"));
    }

    @Test
    public void getNotExistingToppingShouldEmptyOptional() {
        Optional<Topping> result = service.getTopping("not existing");
        Assert.assertTrue(result.isEmpty());
    }
}
