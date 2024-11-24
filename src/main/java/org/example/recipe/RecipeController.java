package org.example.recipe;

import jakarta.validation.Valid;
import org.example.recipe.dto.RecipeResponse;
import org.example.recipe.dto.RegisterRecipeRequest;
import org.example.recipe.dto.UpdateRecipeRequest;
import org.example.recipe.recipeid.RecipeId;
import org.example.recipeitem.RecipeItemService;
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
import java.util.Optional;
import java.util.UUID;

@RestController
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeItemService recipeItemService;

    @PostMapping("/recipes")
    public ResponseEntity<RecipeResponse> registerNewRecipe(@Valid @RequestBody RegisterRecipeRequest recipeRequest) {
        Recipe recipe = Recipe.createRecipe(recipeRequest.getName(), recipeRequest.getTitle(), recipeRequest.getDescription());
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
        Optional<Recipe> recipe = recipeRepository.findById(new RecipeId(recipeId));
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not found");
        }
        recipe.get().addSteps(steps);
        recipeRepository.save(recipe.get());
        URI itemLocation = recipeUri(recipe.get().getId());
        return ResponseEntity.created(itemLocation).body(RecipeResponse.fromRecipe(recipe.orElse(null)));
    }

    @PutMapping(path = "/recipes/{recipeId}/recipeitems")
    @Transactional
    public ResponseEntity<RecipeResponse> addRecipeItemsToRecipe(@PathVariable UUID recipeId, @Valid @RequestBody AddRecipeItemsToRecipeRequest request) {
        List<RecipeItemDto> recipeItems = request.getRecipeItems();
        if (recipeItems.isEmpty()) {
            System.out.println("Empty recipeItem list");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty recipeItem list");
        }
        Recipe savedRecipe;
        try {
           savedRecipe = recipeItemService.addRecipeItemsToRecipe(recipeId, recipeItems);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error adding recipe items to recipe: " + e.getMessage());
        }
        return ResponseEntity.ok().body(RecipeResponse.fromRecipe(savedRecipe));
    }



    @PutMapping(path = "/recipes/{recipeId}")
    @Transactional
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable UUID recipeId, @Valid @RequestBody UpdateRecipeRequest request) {

        if (ObjectUtils.isEmpty(request.getName()) || ObjectUtils.isEmpty(request.getTitle()) || ObjectUtils.isEmpty(request.getDescription()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request params invalid");
        }
        Optional<Recipe> recipe = recipeRepository.findById(new RecipeId(recipeId));
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not found");
        }
        recipe.get().setDescription(request.getDescription());
        recipe.get().setName(request.getName());
        recipe.get().setTitle(request.getTitle());

        recipeRepository.save(recipe.get());
        return ResponseEntity.ok().body(RecipeResponse.fromRecipe(recipe.orElse(null)));
    }

    private URI recipeUri(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
