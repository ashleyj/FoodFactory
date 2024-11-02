package org.example.recipeitem.measurement.dto;

import lombok.*;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterRecipeItemMeasurementRequest {
    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    RecipeItemMeasurementId recipeItemMeasurementId;

    @Getter
    @Setter
    double conversionRation;

    @Getter
    @Setter
    String measurementName;

    @Getter
    @Setter
    String abbreviatedMeasurementName;
}
