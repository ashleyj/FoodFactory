package org.example.recipe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.recipe.recipeid.RecipeId;
import org.example.recipe.step.Step;
import org.example.recipeitem.RecipeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Recipe {

    @Getter
    @Setter
    @EmbeddedId
    @AttributeOverride(name = "recipe_id", column = @Column(name = "recipe_id"))
    public RecipeId recipeId;

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public String title;

    @Getter
    @Setter
    public String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private List<Step> steps;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private List<RecipeItem> recipeItems;

    public Recipe(RecipeId recipeId, String name, String title, String description) {
        this.recipeId = recipeId;
        this.name = name;
        this.title = title;
        this.description = description;
    }

    public UUID getId() {
        return recipeId.getId();
    }

    public static Recipe createRecipe(String name, String title, String description) {
        return new Recipe(RecipeId.generateId(), name, title, description);
    }

    public void addSteps(List<String> s) {
        this.steps = new ArrayList<>();
        s.forEach(stepText -> this.steps.add(Step.createStep(stepText)));
    }
    public void addRecipeItems(List<RecipeItem> items) {
        this.recipeItems = new ArrayList<>();
        items.forEach(recipeItem -> this.recipeItems.add(recipeItem));
    }
}
