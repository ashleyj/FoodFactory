package org.example.item.itemid;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ItemId  implements Serializable {
    @Getter
    protected UUID id;

    public static ItemId generateId() {
        return new ItemId(UUID.randomUUID());
    }
}
