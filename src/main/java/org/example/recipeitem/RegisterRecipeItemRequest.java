package org.example.recipeitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterRecipeItemRequest {

    @Getter
    @Setter
    ItemId itemId;

    @Getter
    @Setter
    int count;

    @Getter
    @Setter
    RecipeItemMeasurementId recipeItemMeasurementId;
}
