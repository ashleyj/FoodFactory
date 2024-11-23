package org.example.recipeitem.measurement;

import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeItemMeasurementRepository extends CrudRepository<RecipeItemMeasurement, RecipeItemMeasurementId> {
    List<RecipeItemMeasurement> findByName(String name);
}
