package org.example.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class RegisterItemRequest {
    @Getter
    @Setter
    @NotBlank(message = "Name is required")
    private String itemName;
}
