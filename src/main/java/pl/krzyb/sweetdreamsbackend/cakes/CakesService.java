package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CakesService {

    private final CakesRepository repository;

    CakesService(CakesRepository repository) {
        this.repository = repository;
    }

    List<Cake> getCakes() {
        return repository.findAll();
    }

    Optional<Cake> getCake(String name) {
        return Optional.ofNullable(repository.findCakeByName(name));
    }

    void addCake(Cake cake) {
        CakesMock.CAKES.add(cake);
    }

    boolean deleteCake(String name) {
        return CakesMock.CAKES.removeIf(cake -> cake.getName().equalsIgnoreCase(name));
    }
}
