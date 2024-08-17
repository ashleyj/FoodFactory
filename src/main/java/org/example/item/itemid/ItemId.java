package org.example.item.itemid;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ItemId  implements Serializable {

    @AttributeOverride(name = "id", column = @Column(name = "item_id"))
    @Getter
    @Setter
    public UUID item_id;

    public static ItemId generateId() {
        return new ItemId(UUID.randomUUID());
    }

    public UUID getId() {
        return item_id;
    }
}
