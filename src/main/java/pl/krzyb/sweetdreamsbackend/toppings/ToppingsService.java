package pl.krzyb.sweetdreamsbackend.toppings;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ToppingsService {
    List<Topping> getToppings() {
        return ToppingsMock.TOPPINGS;
    }

    Optional<Topping> getTopping(String name) {
        return ToppingsMock.TOPPINGS.stream().filter(
                topping -> topping.getName().equalsIgnoreCase(name)).findFirst();
    }
}
