package org.example.recipeitem;

import org.example.item.Item;
import org.example.item.ItemRepository;
import org.example.recipe.Recipe;
import org.example.recipe.RecipeItemDto;
import org.example.recipe.RecipeRepository;
import org.example.recipe.recipeid.RecipeId;
import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.example.recipeitem.measurement.RecipeItemMeasurementRepository;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeItemService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeItemMeasurementRepository recipeItemMeasurementRepository;
    @Autowired
    private ItemRepository itemRepository;

    public Recipe addRecipeItemsToRecipe(UUID recipeId, List<RecipeItemDto> recipeItems) {
        Optional<Recipe> recipe = recipeRepository.findById(new RecipeId(recipeId));
        if (recipe.isEmpty()) {
            throw new IllegalArgumentException("Recipe with id " + recipeId + " not found");
        }
        List<RecipeItem> newRecipeItems = new ArrayList<>();
        recipeItems.forEach(recipeItem -> {
            Item item = itemRepository.findByItemIdId(recipeItem.getItemId());
            Optional<RecipeItemMeasurement> recipeItemMeasurement = recipeItemMeasurementRepository.findById(new RecipeItemMeasurementId(recipeItem.getRecipeItemMeasurementId()));
            if (recipeItemMeasurement.isEmpty()) {
                throw new IllegalArgumentException("Recipe item measurement with id " + recipeItem.getRecipeItemMeasurementId() + " not found");
            }
            if (item == null) {
                throw new IllegalArgumentException("Recipe item with id " + recipeItem.getItemId() + " not found");
            }
            newRecipeItems.add(RecipeItem.create(item.getItemId(), recipeItem.count, recipeItemMeasurement.get().getRecipeItemMeasurementId()));
        });
        recipe.get().addRecipeItems(newRecipeItems);
        recipeRepository.save(recipe.get());
        return recipe.orElse(null);
    }
}
