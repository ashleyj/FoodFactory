package org.example.recipe.recipeid;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RecipeId  implements Serializable {
    public UUID id;

    public static RecipeId generateId() {
        return new RecipeId(UUID.randomUUID());
    }
}
