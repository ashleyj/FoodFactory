package org.example.recipeitem;

import lombok.*;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.recipeitemid.RecipeItemId;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class RecipeItemResponse {

    @Getter
    @Setter
    ItemId itemId;

    @Getter
    @Setter
    RecipeItemId recipeItemId;
}
