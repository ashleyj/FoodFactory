package org.example.recipeitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.item.itemid.ItemId;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterRecipeItemRequest {

    @Getter
    @Setter
    ItemId itemId;
}
