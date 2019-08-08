package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CakesService {
    List<Cake> getCakes() {
        return CakesMock.CAKES;
    }

    Optional<Cake> getCake(String name) {
        return CakesMock.CAKES.stream().filter(cake -> cake.getName().equalsIgnoreCase(name)).
                findFirst();
    }

    void addCake(Cake cake) {
        CakesMock.CAKES.add(cake);
    }

    boolean deleteCake(String name) {
        return CakesMock.CAKES.removeIf(cake -> cake.getName().equalsIgnoreCase(name));
    }
}
