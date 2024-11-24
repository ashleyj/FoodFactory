package org.example.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterRecipeRequest {

    @Getter
    @Setter
    @NotBlank(message = "Name is required")
    String name;

    @Getter
    @Setter
    @NotBlank(message = "Title is required")
    String title;

    @Getter
    @Setter
    @NotBlank(message = "Description is required")
    String description;
}
