package org.example.recipe.step;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StepId  implements Serializable {
    @AttributeOverride(name = "id", column = @Column(name = "step_id"))
    public UUID step_id;

    public static StepId generateId() {
        return new StepId(UUID.randomUUID());
    }
}
