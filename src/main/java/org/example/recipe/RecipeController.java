package org.example.recipe;

import jakarta.validation.Valid;
import org.example.recipe.step.Step;
import org.example.recipe.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    StepRepository stepRepository;

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> registerNewRecipe(@Valid @RequestBody Recipe recipeRequest) {
        Recipe recipe = Recipe.createRecipe(recipeRequest.getName(), recipeRequest.title, recipeRequest.description);
        recipeRepository.save(recipe);
        URI newItemLocation = recipeUri(recipe.getId());
        return ResponseEntity.created(newItemLocation).body(recipe);
    }

    @PostMapping(path = "/recipes/{recipeId}/steps")
    public ResponseEntity<Recipe> addStepToRecipe(@PathVariable UUID recipeId, @Valid @RequestBody Step stepRequest) {
        Step step = Step.createStep(stepRequest.getStepText());

        if (step.stepId == null) {
            return ResponseEntity.badRequest().build();
        }
        Recipe recipe = recipeRepository.findByRecipeIdId(recipeId);
        if (recipe == null) {
            return ResponseEntity.badRequest().build();
        }
        stepRepository.save(step);

        recipe.addStep(step);
        recipeRepository.save(recipe);
        URI itemLocation = recipeUri(recipe.getId());
        return ResponseEntity.created(itemLocation).body(recipe);
    }

    private URI recipeUri(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
