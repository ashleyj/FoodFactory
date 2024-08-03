package org.example.recipeitem.measurement.recipeitemmeasurementid;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RecipeItemMeasurementId {
    public UUID id;

    public static RecipeItemMeasurementId generateId() {
        return new RecipeItemMeasurementId(UUID.randomUUID());
    }
}
