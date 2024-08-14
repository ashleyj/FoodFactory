package org.example.recipe;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.recipe.recipeid.RecipeId;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "recipe_id"))
    public RecipeId recipeId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NotBlank(message = "Name is required")
    public String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NotBlank(message = "Title is required")
    public String title;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NotBlank(message = "Description is required")
    public String description;


    public UUID getId() {
        return recipeId.id;
    }

    public static Recipe createRecipe(String name, String title, String description) {
        return new Recipe(RecipeId.generateId(), name, title, description);
    }
}
