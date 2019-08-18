package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cakes")
public class CakesController {

    private CakesService service;

    CakesController(CakesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cake> getCakes() {
        return service.getCakes();
    }

    @GetMapping("/{name}")
    public Cake getCake(@PathVariable String name) {
        return service.getCake(name);
    }

    @PostMapping
    public Cake addCake(@RequestBody Cake cake) {
        return service.addCake(cake);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCake(@PathVariable String name) {
        boolean isRemoved = service.deleteCake(name);
        if (!isRemoved) throw new CakeNotFoundException();
    }
}
