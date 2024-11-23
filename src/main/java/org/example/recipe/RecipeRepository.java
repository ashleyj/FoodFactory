package org.example.recipe;

import org.example.recipe.recipeid.RecipeId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, RecipeId> {
}
