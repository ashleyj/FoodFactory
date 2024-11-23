package org.example.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.recipe.Recipe;
import org.example.recipe.recipeid.RecipeId;
import org.example.recipe.step.Step;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {

    public UUID recipeId;

    public String name;

    public String description;

    public String title;

    public List<Step> steps;

    public static RecipeResponse fromRecipe(Recipe recipe) {
        return new RecipeResponse(recipe.getRecipeId().getId(), recipe.getName(), recipe.getDescription(), recipe.getTitle(), recipe.getSteps());
    }
}
