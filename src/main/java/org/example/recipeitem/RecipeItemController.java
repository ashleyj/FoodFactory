package org.example.recipeitem;

import org.example.item.Item;
import org.example.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class RecipeItemController {

    @Autowired
    RecipeItemRepository recipeItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/recipeitems")
    public ResponseEntity<RecipeItem> registerNewRecipeItem(@RequestBody RecipeItem recipeItemRequest) {
        Item item = itemRepository.findByItemIdId(recipeItemRequest.itemId.id);
        if (item == null) {
            return ResponseEntity.badRequest().build();
        }
        RecipeItem recipeItem = RecipeItem.create(recipeItemRequest.getItemId());
        recipeItemRepository.save(recipeItem);
        URI newRecipeItemLocation = itemUri(recipeItem.getItemId().id);
        return ResponseEntity.created(newRecipeItemLocation).body(recipeItem);
    }

    URI itemUri(UUID id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
    }
}
