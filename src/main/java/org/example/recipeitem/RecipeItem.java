package org.example.recipeitem;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
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
    @AttributeOverride(name ="id", column = @Column(name = "item_id"))
    public ItemId itemId;

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
    @AttributeOverride(name ="id", column = @Column(name = "recipe_item_measurement_id"))
    public RecipeItemMeasurementId recipeItemMeasurementId;

    public static RecipeItem create(ItemId itemId, int count, RecipeItemMeasurementId recipeItemMeasurementId) {
        return new RecipeItem(RecipeItemId.generateId(), itemId, count, recipeItemMeasurementId);
    }
}
