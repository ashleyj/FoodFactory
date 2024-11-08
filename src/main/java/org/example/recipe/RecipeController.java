package org.example.recipe;

import jakarta.validation.Valid;
import org.example.recipe.step.Step;
import org.example.recipe.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    StepRepository stepRepository;

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> registerNewRecipe(@Valid @RequestBody Recipe recipeRequest) {
        Recipe recipe = Recipe.createRecipe(recipeRequest.name, recipeRequest.title, recipeRequest.description);
        recipeRepository.save(recipe);
        URI newItemLocation = recipeUri(recipe.getId());
        return ResponseEntity.created(newItemLocation).body(recipe);
    }

    @PostMapping(path = "/recipes/{recipeId}/steps")
    @Transactional
    public ResponseEntity<Recipe> addStepToRecipe(@PathVariable UUID recipeId, @Valid @RequestBody AddStepToRecipeRequest request) {
        List<String> steps = request.getSteps();
        if (steps.isEmpty()) {
            System.out.println("Empty step set");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty step set");
        }
        Recipe recipe = recipeRepository.findByRecipeIdId(recipeId);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not found");
        }
        recipe.addSteps(steps);
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
