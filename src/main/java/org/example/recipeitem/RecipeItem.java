package org.example.recipeitem;

import jakarta.persistence.*;
import lombok.*;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.recipeitemid.RecipeItemId;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecipeItem {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @EmbeddedId
    @AttributeOverride(name ="id", column = @Column(name = "recipe_item_id"))
    public RecipeItemId recipeItemId;


    @Getter
    @Setter(AccessLevel.PRIVATE)
    @Embedded
    @Column(name="item_id")
    public ItemId itemId;


    public static RecipeItem create(ItemId itemId) {
        return new RecipeItem(RecipeItemId.generateId(), itemId);
    }
}
