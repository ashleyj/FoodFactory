package org.example.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.recipe.recipeid.RecipeId;
import org.example.recipe.step.Step;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {

    @Getter
    @Setter
    public RecipeId recipeId;

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public List<Step> steps;
}
