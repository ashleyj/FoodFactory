package org.example.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.recipe.step.Step;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class AddStepToRecipeRequest {

    @Getter
    @Setter
    private UUID recipeId;

    @Getter
    @Setter
    private String stepText;

}
