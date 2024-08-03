package org.example.recipe;

import jakarta.validation.Valid;
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

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> registerNewRecipe(@Valid @RequestBody Recipe recipeRequest) {
        Recipe recipe = Recipe.createRecipe(recipeRequest.getName(), recipeRequest.title, recipeRequest.description);
        recipeRepository.save(recipe);
        URI newItemLocation = recipeUri(recipe.getId());
        return ResponseEntity.created(newItemLocation).body(recipe);
    }

    private URI recipeUri(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
