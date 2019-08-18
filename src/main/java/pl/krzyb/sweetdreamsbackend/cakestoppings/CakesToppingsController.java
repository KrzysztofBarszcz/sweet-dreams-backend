package pl.krzyb.sweetdreamsbackend.cakestoppings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzyb.sweetdreamsbackend.cakes.Cake;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;

import java.util.List;

@RestController
@RequestMapping("cakes/{cakeName}/toppings")
public class CakesToppingsController {
    private CakesToppingsService service;

    CakesToppingsController(CakesToppingsService service) {
        this.service = service;
    }

    @PostMapping("/{toppingName}")
    public Cake addToppingToCake(@PathVariable String cakeName, @PathVariable String toppingName) {
        return service.addToppingToCake(cakeName, toppingName);
    }

    @GetMapping
    public List<Topping> getToppings(@PathVariable String cakeName) {
        return service.getToppingsForCake(cakeName);
    }

    @DeleteMapping("/{toppingName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToppingOfCake(@PathVariable String cakeName, @PathVariable String toppingName) {
        service.deleteToppingForCake(cakeName, toppingName);
    }
}
