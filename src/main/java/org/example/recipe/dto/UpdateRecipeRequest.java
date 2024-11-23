package org.example.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecipeRequest {

    @Getter
    @Setter
    UUID recipeId;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    String description;
}
