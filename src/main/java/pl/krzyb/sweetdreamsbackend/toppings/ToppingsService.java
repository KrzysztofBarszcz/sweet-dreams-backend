package pl.krzyb.sweetdreamsbackend.toppings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ToppingsService {
    private ToppingsRepository repository;

    ToppingsService(ToppingsRepository repository) {
        this.repository = repository;
    }

    List<Topping> getToppings() {
        var toppings = repository.findAll();
        log.debug("Found {} toppings.", toppings.size());
        return toppings;
    }

    public Topping getTopping(String name) {
        var topping = Optional.ofNullable(repository.findToppingByNameIgnoreCase(name));
        if(topping.isPresent())
            log.debug("Topping {} found.", name);
        return topping.orElseThrow(() -> {
            log.debug("Topping {} not found.", name);
            throw new ToppingNotFoundException();
        });
    }

    Topping addTopping(Topping topping) {
        var savedTopping = repository.save(topping);
        log.debug("Added new topping: {}.", savedTopping.getName());
        return savedTopping;
    }

    public Topping saveTopping(Topping topping) {
        return repository.save(topping);
    }

    boolean deleteTopping(String name) {
        Topping toppingToRemove = repository.findToppingByNameIgnoreCase(name);
        if (toppingToRemove != null) {
            repository.delete(toppingToRemove);
            log.debug("Topping {} removed", name);
            return true;
        } else {
            log.debug("Tried to remove topping: {} but it does not exist.", name);
            return false;
        }
    }
}
