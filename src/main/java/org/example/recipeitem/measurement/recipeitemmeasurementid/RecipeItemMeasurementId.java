package org.example.recipeitem.measurement.recipeitemmeasurementid;

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
    public UUID id;

    public static RecipeItemMeasurementId generateId() {
        return new RecipeItemMeasurementId(UUID.randomUUID());
    }
}
