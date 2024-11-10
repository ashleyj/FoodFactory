package org.example.recipe.step;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.recipe.Recipe;
import org.example.recipe.recipeid.RecipeId;
import org.example.recipeitem.measurement.RecipeItemMeasurement;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class Step {
@Getter
    @Setter(AccessLevel.PRIVATE)
    @EmbeddedId
    public StepId stepId;

    @Getter
    @Setter
    @NotBlank(message = "Step text is required")
    private String stepText;

    public Step(StepId stepId, String stepText) {
        this.stepId = stepId;
        this.stepText = stepText;
    }
    public static Step createStep(String stepText) {
        return new Step(StepId.generateId(), stepText);
    }
}
