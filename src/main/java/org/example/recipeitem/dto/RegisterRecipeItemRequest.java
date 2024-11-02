package org.example.recipeitem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterRecipeItemRequest {

    @Getter
    @Setter
    UUID itemId;

    @Getter
    @Setter
    int count;

    @Getter
    @Setter
    UUID recipeItemMeasurementId;
}
