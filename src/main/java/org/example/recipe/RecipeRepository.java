package org.example.recipe;

import org.example.recipe.recipeid.RecipeId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, RecipeId> {
    Recipe findByRecipeIdId(UUID id);
}
