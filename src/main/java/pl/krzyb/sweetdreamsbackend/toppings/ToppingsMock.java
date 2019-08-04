package pl.krzyb.sweetdreamsbackend.toppings;

import java.util.List;

public class ToppingsMock {

    static final List<Topping> TOPPINGS = prepareToppings();

    private static List<Topping> prepareToppings() {
        return List.of(new Topping("Whipped cream"), new Topping("Almonds"),
                new Topping("Poppy"), new Topping("Chocolate"));
    }
}
