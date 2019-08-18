package pl.krzyb.sweetdreamsbackend.cakestoppings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.krzyb.sweetdreamsbackend.cakes.Cake;
import pl.krzyb.sweetdreamsbackend.cakes.CakeNotFoundException;
import pl.krzyb.sweetdreamsbackend.cakes.CakesRepository;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingNotFoundException;
import pl.krzyb.sweetdreamsbackend.toppings.ToppingsRepository;

import java.util.List;

@Service
@Slf4j
public class CakesToppingsService {

    private final CakesRepository cakesRepository;
    private final ToppingsRepository toppingsRepository;

    CakesToppingsService(CakesRepository cakesRepository, ToppingsRepository toppingsRepository) {
        this.cakesRepository = cakesRepository;
        this.toppingsRepository = toppingsRepository;
    }

    Cake addToppingToCake(String cakeName, String toppingName) {
        Cake cake = cakesRepository.findCakeByNameIgnoreCase(cakeName);
        Topping topping = toppingsRepository.findToppingByNameIgnoreCase(toppingName);

        if (cake == null)
            throw new CakeNotFoundException();

        if (topping == null)
            throw new ToppingNotFoundException();

        cake.getToppings().add(topping);
        log.debug("Added new topping: {}, to cake: {}", topping.getName(), cake.getName());
        return cakesRepository.save(cake);
    }

    List<Topping> getToppingsForCake(String cakeName) {
        Cake cake = cakesRepository.findCakeByNameIgnoreCase(cakeName);

        if (cake == null)
            throw new CakeNotFoundException();

        log.debug("Found {} toppings", cake.getToppings().size());
        return cake.getToppings();
    }

    public void deleteToppingForCake(String cakeName, String toppingName) {
        Cake cake = cakesRepository.findCakeByNameIgnoreCase(cakeName);

    }
}
