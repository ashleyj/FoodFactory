package org.example.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterRecipeRequest {

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    String description;
}
