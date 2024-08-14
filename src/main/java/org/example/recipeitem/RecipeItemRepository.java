package org.example.recipeitem;

import org.example.recipeitem.recipeitemid.RecipeItemId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeItemRepository extends CrudRepository<RecipeItem, RecipeItemId> {
}
