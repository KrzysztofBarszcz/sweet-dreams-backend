package pl.krzyb.sweetdreamsbackend.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findIngredientByNameIgnoreCase(String name);
}
