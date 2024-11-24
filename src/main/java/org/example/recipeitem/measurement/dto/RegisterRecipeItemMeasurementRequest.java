package org.example.recipeitem.measurement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterRecipeItemMeasurementRequest {
    @Getter
    @Setter
    @NotBlank(message = "Name is required")
    String name;

    @Getter
    @Setter
    RecipeItemMeasurementId recipeItemMeasurementId;

    @Getter
    @Setter
    @NotBlank(message = "Conversion Ratio is required")
    double conversionRatio;

    @Getter
    @Setter
    @NotBlank(message =  "Name is required")
    String measurementName;

    @Getter
    @Setter
    @NotBlank(message = "Abbreviation Name is required")
    String abbreviatedMeasurementName;
}
