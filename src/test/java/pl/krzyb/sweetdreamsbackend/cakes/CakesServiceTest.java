package pl.krzyb.sweetdreamsbackend.cakes;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class CakesServiceTest {

    CakesService service = new CakesService();

    @Test
    public void getOneShouldReturnCake() {
        Optional<Cake> result = service.getCake("Pie");
        Assert.assertThat(result.get().getName(), equalTo("Pie"));
    }

    @Test
    public void getAllShouldReturnAllCakes() {
        List<Cake> result = service.getCakes();

        Assert.assertThat(result, hasSize(4));
    }
}
