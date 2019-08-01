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
        return CakesMock.CAKES.stream().filter(cake -> cake.getName().equals(name)).
                findFirst();
    }
}
