package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CakesRepository extends JpaRepository<Cake, Long> {
    Cake findCakeByNameIgnoreCase(String name);
}
