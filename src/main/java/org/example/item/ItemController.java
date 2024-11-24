package org.example.item;

import org.example.item.dto.RegisterItemRequest;
import org.example.item.itemid.ItemId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/items")
    public ResponseEntity<Item> registerNewItem(@RequestBody RegisterItemRequest itemRequest) {
        Item item = Item.craeteItem(itemRequest.getItemName());
        itemRepository.save(item);
        URI newItemLocation = itemUri(item.getItemId().getId());
        return ResponseEntity.created(newItemLocation).body(item);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getExistingItem(@PathVariable UUID id) {
        Optional<Item> item = itemRepository.findById(new ItemId(id));
        return ResponseEntity.of(item);
    }

    URI itemUri(UUID id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
    }

}
