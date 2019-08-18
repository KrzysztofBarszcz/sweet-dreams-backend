package pl.krzyb.sweetdreamsbackend.cakes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Slf4j
public class CakesService {

    private final CakesRepository repository;

    CakesService(CakesRepository repository) {
        this.repository = repository;
    }

    List<Cake> getCakes() {
        var cakes = repository.findAll();
        log.debug("Found {} cakes.", cakes.size());
        return cakes;
    }

    Cake getCakeOrThrow(String name) {
        var cake = Optional.ofNullable(repository.findCakeByNameIgnoreCase(name));
        cake.ifPresent((elem) -> log.debug("Cake {} found.", name));
        return cake.orElseThrow(()-> {
            log.debug("Cake {} not found.", name);
            throw new CakeNotFoundException();
        });
    }

    Cake addCake(Cake cake) {
        Cake savedCake = repository.save(cake);
        log.debug("Added new cake: {}.", cake.getName());
        return savedCake;
    }

    boolean deleteCake(String name) {
        Cake cakeToRemove = repository.findCakeByNameIgnoreCase(name);
        if (cakeToRemove != null) {
            repository.delete(cakeToRemove);
            log.debug("Cake removed: {}.", name);
            return true;
        } else {
            log.debug("Tried to remove cake: {} but it does not exist.", name);
            return false;
        }
    }
}
