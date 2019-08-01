package pl.krzyb.sweetdreamsbackend.cakes;

import java.util.List;

public class CakesMock {
    static final List<Cake> CAKES = prepareCakes();

    private static List<Cake> prepareCakes() {
        return List.of(new Cake("Pie"), new Cake("Eclair"),
                new Cake("Cheese cake"), new Cake("Birthday cake"));
    }
}
