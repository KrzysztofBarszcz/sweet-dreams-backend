package pl.krzyb.sweetdreamsbackend.toppings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToppingsRepository extends JpaRepository<Topping, Long> {
    Topping findToppingByNameIgnoreCase(String name);
}
