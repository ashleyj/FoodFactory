package org.example.item.itemid;
import jakarta.persistence.*;
import lombok.*;
import org.example.item.Item;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ItemId  implements Serializable {

    @Getter
    @Setter
    public UUID id;

    public static ItemId generateId() {
        return new ItemId(UUID.randomUUID());
    }
}
