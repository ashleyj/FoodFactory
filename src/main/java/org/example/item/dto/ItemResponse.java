package org.example.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.item.itemid.ItemId;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    @Setter
    @Getter
    public ItemId itemId;

    @Getter
    @Setter
    public String itemName;

    public UUID getId() {
        return this.itemId.getId();
    }

}
