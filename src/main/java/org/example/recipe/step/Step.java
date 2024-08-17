package org.example.recipe.step;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.recipe.recipeid.RecipeId;

import java.util.UUID;

@Entity
@AllArgsConstructor
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

    public static Step createStep(String stepText) {
        return new Step(StepId.generateId(), stepText);
    }
}
