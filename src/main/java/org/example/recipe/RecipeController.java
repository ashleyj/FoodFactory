package org.example.recipe;

import jakarta.validation.Valid;
import org.example.recipe.dto.RecipeResponse;
import org.example.recipe.dto.UpdateRecipeRequest;
import org.example.recipe.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    StepRepository stepRepository;

    @PostMapping("/recipes")
    public ResponseEntity<RecipeResponse> registerNewRecipe(@Valid @RequestBody Recipe recipeRequest) {
        Recipe recipe = Recipe.createRecipe(recipeRequest.name, recipeRequest.title, recipeRequest.description);
        recipeRepository.save(recipe);
        URI newItemLocation = recipeUri(recipe.getId());
        return ResponseEntity.created(newItemLocation).body(RecipeResponse.fromRecipe(recipe));
    }

    @GetMapping("/recipes")
    public ResponseEntity<Iterable<Recipe>> getRecipes() {
        return ResponseEntity.ok().body(recipeRepository.findAll());
    }

    @PutMapping(path = "/recipes/{recipeId}/steps")
    @Transactional
    public ResponseEntity<RecipeResponse> addStepToRecipe(@PathVariable UUID recipeId, @Valid @RequestBody AddStepToRecipeRequest request) {
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
        return ResponseEntity.created(itemLocation).body(RecipeResponse.fromRecipe(recipe));
    }



    @PutMapping(path = "/recipes/{recipeId}")
    @Transactional
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable UUID recipeId, @Valid @RequestBody UpdateRecipeRequest request) {

        if (ObjectUtils.isEmpty(request.getName()) || ObjectUtils.isEmpty(request.getTitle()) || ObjectUtils.isEmpty(request.getDescription()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request params invalid");
        }
        Recipe recipe = recipeRepository.findByRecipeIdId(recipeId);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not found");
        }
        recipe.setDescription(request.getDescription());
        recipe.setName(request.getName());
        recipe.setTitle(request.getTitle());

        recipeRepository.save(recipe);
        return ResponseEntity.ok().body(RecipeResponse.fromRecipe(recipe));
    }

    private URI recipeUri(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
