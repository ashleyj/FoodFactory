package org.example.recipeitem;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
import org.example.recipeitem.recipeitemid.RecipeItemId;

@NoArgsConstructor
@Entity
public class RecipeItem {

    @Getter
    @Setter
    @EmbeddedId
    public RecipeItemId recipeItemId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "item_id"))
    private ItemId itemId;

    @Getter
    @Setter
    /**
     * Amount of this item within the recipe in relation to RecipeItemMeasuremnetId
     */
    @Min(value = 1, message = "Amount must be greater than 1")
    public int count;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "recipe_item_measurement_id"))
    public RecipeItemMeasurementId recipeItemMeasurementId;

    public RecipeItem(RecipeItemId recipeItemId, ItemId itemId, int count, RecipeItemMeasurementId recipeItemMeasurementId) {
        this.recipeItemId = recipeItemId;
        this.itemId = itemId;
        this.count = count;
        this.recipeItemMeasurementId = recipeItemMeasurementId;

    }

    public static RecipeItem create(ItemId itemId, int count, RecipeItemMeasurementId recipeItemMeasurementId) {
        return new RecipeItem(RecipeItemId.generateId(), itemId, count, recipeItemMeasurementId);
    }
}
