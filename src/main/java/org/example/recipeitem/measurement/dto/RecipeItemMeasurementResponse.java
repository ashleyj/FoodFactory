package org.example.recipeitem.measurement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;

@AllArgsConstructor
@NoArgsConstructor
public class RecipeItemMeasurementResponse {

    @Getter
    @Setter
    public RecipeItemMeasurementId recipeItemMeasurementId;

    @Getter
    @Setter
    public String name;
}
