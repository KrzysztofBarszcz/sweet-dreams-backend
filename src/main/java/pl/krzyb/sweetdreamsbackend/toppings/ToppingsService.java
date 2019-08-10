package pl.krzyb.sweetdreamsbackend.toppings;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToppingsService {
    private ToppingsRepository repository;

    ToppingsService(ToppingsRepository repository) {
        this.repository = repository;
    }

    List<Topping> getToppings() {
        return repository.findAll();
    }

    Optional<Topping> getTopping(String name) {
        return Optional.ofNullable(repository.findToppingByNameIgnoreCase(name));
    }

    Topping addTopping(Topping topping) {
        return repository.save(topping);
    }

    boolean deleteTopping(String name) {
        Topping toppingToRemove = repository.findToppingByNameIgnoreCase(name);
        if (toppingToRemove != null) {
            repository.delete(toppingToRemove);
            return true;
        } else return false;
    }
}
