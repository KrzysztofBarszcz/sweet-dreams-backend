package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cakes")
public class CakesController {

    @GetMapping
    public List<Cake> getCakes() {
        return CakesMock.CAKES;
    }

    @GetMapping("/{name}")
    public Optional<Cake> getCake(@PathVariable String name) {
        return CakesMock.CAKES.stream().filter(cake -> cake.getName().equalsIgnoreCase(name)).findFirst();
    }
}
