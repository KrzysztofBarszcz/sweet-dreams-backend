package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Cake getCake(@PathVariable String name) {
        Optional<Cake> result = CakesMock.CAKES.stream().filter(
                cake -> cake.getName().equalsIgnoreCase(name)).findFirst();

        return result.orElseThrow(CakeNotFoundException::new);
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class CakeNotFoundException extends RuntimeException {
    CakeNotFoundException() {
        super("Cake with the given name does not exist");
    }
}
