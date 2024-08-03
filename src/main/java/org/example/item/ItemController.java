package org.example.item;

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
    public ResponseEntity<Item> registerNewItem(@RequestBody Item itemRequest) {
        Item item = Item.craeteItem(itemRequest.getName());
        itemRepository.save(item);
        URI newItemLocation = itemUri(item.getId());
        return ResponseEntity.created(newItemLocation).body(item);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getExistingItem(@PathVariable UUID id) {
        Item item = itemRepository.findByItemIdId(id);
        return ResponseEntity.of(Optional.of(item));
    }

    URI itemUri(UUID id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
    }

}
