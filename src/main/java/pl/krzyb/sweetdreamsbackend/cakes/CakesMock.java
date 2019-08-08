package pl.krzyb.sweetdreamsbackend.cakes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CakesMock {
    static List<Cake> CAKES = prepareCakes();

    private static List<Cake> prepareCakes() {
        return new ArrayList<>(Arrays.asList(new Cake("Pie"), new Cake("Eclair"),
                new Cake("Cheese cake"), new Cake("Birthday cake")));
    }

    public static void refreshCakes() {
        CAKES = prepareCakes();
    }
}
