package org.example.recipeitem.measurement;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecipeItemMeasurement {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @EmbeddedId
    @Column(name = "recipe_item_measurement_id", nullable = false)
    public RecipeItemMeasurementId recipeItemMeasurementId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NotBlank(message = "Must contain name")
    private String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    // conversion to weight
    private double conversionRatio;

    @Getter
    @Setter (AccessLevel.PRIVATE)
    @NotBlank(message = "Must contain a measurement abbreviation")
    public String abbreviatedMeasurementName;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NotBlank(message = "Must contain a measurement name")
    private String measurementName;

    public static RecipeItemMeasurement CreateRecipeItemMeasurement(String name,
                                                                    double conversionRatio,
                                                                    String measurementName,
                                                                    String abbreviatedMeasurementName) {
        return new RecipeItemMeasurement(
                RecipeItemMeasurementId.generateId(),
                name,
                conversionRatio,
                measurementName,
                abbreviatedMeasurementName);
    }
}
