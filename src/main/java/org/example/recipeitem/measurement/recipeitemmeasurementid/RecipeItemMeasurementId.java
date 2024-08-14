package org.example.recipeitem.measurement.recipeitemmeasurementid;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RecipeItemMeasurementId {
    @Getter
    @Setter
    public UUID recipe_item_measurement_d;

    public static RecipeItemMeasurementId generateId() {
        return new RecipeItemMeasurementId(UUID.randomUUID());
    }
}
