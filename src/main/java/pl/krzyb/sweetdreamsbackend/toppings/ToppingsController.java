package pl.krzyb.sweetdreamsbackend.toppings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return service.getTopping(name);
    }

    @PostMapping
    public Topping addTopping(@RequestBody Topping topping) {
        return service.addTopping(topping);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopping(@PathVariable String name) {
        boolean isRemoved = service.deleteTopping(name);
        if (!isRemoved) throw new ToppingNotFoundException();
    }
}

