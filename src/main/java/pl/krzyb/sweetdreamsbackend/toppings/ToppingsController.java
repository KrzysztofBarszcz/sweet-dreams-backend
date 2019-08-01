package pl.krzyb.sweetdreamsbackend.toppings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("toppings")
public class ToppingsController {

    @GetMapping
    public List<Topping> getToppings() {
        return Collections.emptyList();
    }
}
