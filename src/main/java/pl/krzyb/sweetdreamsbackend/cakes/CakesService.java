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
        return Optional.ofNullable(repository.findCakeByNameIgnoreCase(name));
    }

    void addCake(Cake cake) {
        repository.save(cake);
    }

    boolean deleteCake(String name) {
        Cake cakeToRemove = repository.findCakeByNameIgnoreCase(name);
        if(cakeToRemove != null) {
            repository.delete(cakeToRemove);
            return true;
        }
        else return false;
    }
}
