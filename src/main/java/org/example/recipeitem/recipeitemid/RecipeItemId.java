package org.example.recipeitem.recipeitemid;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RecipeItemId implements Serializable {
    @Getter
    public UUID id;

    public static RecipeItemId generateId() {
        return new RecipeItemId(UUID.randomUUID());
    }

}
