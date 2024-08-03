package org.example.item;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import org.example.item.itemid.ItemId;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "item_id"))
    public ItemId itemId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String name;

    public UUID getId() {
        return itemId.id;
    }

    public static Item craeteItem(String name) {
        return new Item(ItemId.generateId(), name);
    }
}
