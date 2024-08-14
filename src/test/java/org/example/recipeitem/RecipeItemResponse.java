package org.example.recipeitem;

import lombok.*;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
import org.example.recipeitem.recipeitemid.RecipeItemId;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class RecipeItemResponse {

    @Getter
    @Setter
    public  ItemId itemId;

    @Getter
    @Setter
    RecipeItemMeasurementId recipeItemMeasurementId;

    @Getter
    @Setter
    RecipeItemId recipeItemId;
}
