package org.example.recipeitem;

import org.example.item.Item;
import org.example.item.ItemRepository;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.dto.RegisterRecipeItemRequest;
import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.example.recipeitem.measurement.RecipeItemMeasurementRepository;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RecipeItemController {

    @Autowired
    RecipeItemRepository recipeItemRepository;

    @Autowired
    RecipeItemMeasurementRepository recipeItemMeasurementRepository;

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/recipeitems")
    public ResponseEntity<RecipeItem> registerNewRecipeItem(@RequestBody RegisterRecipeItemRequest recipeItemRequest) {
        Optional<Item> item = itemRepository.findById(new ItemId(recipeItemRequest.getItemId()));
        Optional<RecipeItemMeasurement> recipeItemMeasurement = recipeItemMeasurementRepository.findById(new RecipeItemMeasurementId(recipeItemRequest.getRecipeItemMeasurementId()));

        if (item.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (recipeItemMeasurement.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        RecipeItem recipeItem = RecipeItem.create(item.get().getItemId(), recipeItemRequest.getCount(), recipeItemMeasurement.get().getRecipeItemMeasurementId());
        recipeItemRepository.save(recipeItem);
        URI newRecipeItemLocation = itemUri(recipeItem.getRecipeItemId().getId());
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
