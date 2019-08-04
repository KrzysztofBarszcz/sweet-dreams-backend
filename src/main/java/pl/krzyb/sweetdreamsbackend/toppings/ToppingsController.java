package pl.krzyb.sweetdreamsbackend.toppings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("toppings")
public class ToppingsController {

    private ToppingsService service;

    ToppingsController(ToppingsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Topping> getToppings() {
        return service.getToppings();
    }

    @GetMapping("/{name}")
    public Topping getTopping(@PathVariable String name) {
        Optional<Topping> result = service.getTopping(name);

        return result.orElseThrow(ToppingNotFoundException::new);
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ToppingNotFoundException extends RuntimeException {
    ToppingNotFoundException() {
        super("Topping with the given name does not exist");
    }
}