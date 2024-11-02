package org.example.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class AddStepToRecipeRequest {

    @Getter
    @Setter
    public UUID recipeId;

    @Getter
    @Setter
    public List<String> steps;

}
