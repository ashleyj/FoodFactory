package org.example.item;

import org.example.item.itemid.ItemId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends CrudRepository<Item, ItemId> {
    Item findByItemIdId(UUID recipeItemId);

}
