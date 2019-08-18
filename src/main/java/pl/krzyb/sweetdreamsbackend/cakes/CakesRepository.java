package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;

public interface CakesRepository extends JpaRepository<Cake, Long> {
    Cake findCakeByNameIgnoreCase(String name);
}
