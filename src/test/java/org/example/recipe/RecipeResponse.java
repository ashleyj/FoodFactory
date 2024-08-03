package org.example.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.recipe.recipeid.RecipeId;

@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {

    @Getter
    @Setter
    public RecipeId recipeId;

    @Getter
    @Setter
    public String name;
}
