package org.example.item;

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
    public String name;

    public UUID getId() {
        return this.itemId.getId();
    }

}
