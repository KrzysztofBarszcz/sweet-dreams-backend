package pl.krzyb.sweetdreamsbackend.cakestoppings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.krzyb.sweetdreamsbackend.cakes.Cake;
import pl.krzyb.sweetdreamsbackend.cakes.CakesService;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsService;

import java.util.List;

@Service
@Slf4j
public class CakesToppingsService {

    private final CakesService cakesService;
    private final ToppingsService toppingsService;

    CakesToppingsService(CakesService cakesService, ToppingsService toppingsService) {
        this.cakesService = cakesService;
        this.toppingsService = toppingsService;
    }

    Cake addToppingToCake(String cakeName, String toppingName) {
        Cake cake = cakesService.getCake(cakeName);
        Topping topping = toppingsService.getTopping(toppingName);

        cake.getToppings().add(topping);
        log.debug("Added new topping: {}, to cake: {}", topping.getName(), cake.getName());
        return cakesService.saveCake(cake);
    }

    List<Topping> getToppingsForCake(String cakeName) {
        Cake cake = cakesService.getCake(cakeName);

        log.debug("Found {} toppings", cake.getToppings().size());
        return cake.getToppings();
    }

    public void deleteToppingForCake(String cakeName, String toppingName) {
        Cake cake = cakesService.getCake(cakeName);
        Topping topping = toppingsService.getTopping(toppingName);
        var toppings = cake.getToppings();
        if (!toppings.contains(topping))
            throw new CakeDoesNotHaveToppingException(cakeName, toppingName);
        toppings.remove(topping);

        cakesService.saveCake(cake);
    }
}
