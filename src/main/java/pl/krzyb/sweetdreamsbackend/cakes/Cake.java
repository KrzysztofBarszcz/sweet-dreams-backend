package pl.krzyb.sweetdreamsbackend.cakes;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.krzyb.sweetdreamsbackend.toppings.Topping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Cake {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NonNull
    private String name;

    @ManyToMany
    private List<Topping> toppings = new ArrayList<>();

    @NonNull
    private Double cost;
}
