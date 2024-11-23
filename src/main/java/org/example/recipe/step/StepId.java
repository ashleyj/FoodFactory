package org.example.recipe.step;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StepId  implements Serializable {
    public UUID id;

    public static StepId generateId() {
        return new StepId(UUID.randomUUID());
    }
}
