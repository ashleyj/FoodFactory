package org.example.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class RegisterItemRequest {
    @Getter
    @Setter
    private String itemName;
}
