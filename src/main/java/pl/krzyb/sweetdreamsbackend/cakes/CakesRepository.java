package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CakesRepository extends JpaRepository<Cake, Long> {
    Cake findCakeByName(String name);
}
