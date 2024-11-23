package org.example.recipe;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class RecipeItemDto {

    @Getter
    @Setter
    @NotBlank(message = "ItemID must be provided")
    private UUID itemId;

    @Getter
    @Setter
    @Min(value = 1, message = "Amount must be greater than 1")
    public int count;

    @Getter
    @Setter
    @NotBlank(message = "RecipeItemMeasurementID must be provided")
    public UUID recipeItemMeasurementId;
}
