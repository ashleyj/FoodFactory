package org.example.recipeitem.measurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RecipeItemMeasurementController {

    @Autowired
    public RecipeItemMeasurementRepository recipeItemMeasurementRepository;

    @PostMapping("/recipeitemmeasurements")
    public ResponseEntity<RecipeItemMeasurement> registerNewRecipeItem(@RequestBody RecipeItemMeasurement recipeItemMeasurementRequest) {

        List<RecipeItemMeasurement> recipeItemMeasurementList = recipeItemMeasurementRepository.findByName(recipeItemMeasurementRequest.getName());
        if (!recipeItemMeasurementList.isEmpty()) {
            System.out.println("Entity with name: " + recipeItemMeasurementRequest.getName() + " already exists with id: " + recipeItemMeasurementList.stream().findFirst().get().getRecipeItemMeasurementId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity with name: " + recipeItemMeasurementRequest.getName() + " already exists with id: " + recipeItemMeasurementList.stream().findFirst().get().getRecipeItemMeasurementId());
        }

        RecipeItemMeasurement recipeItemMeasurement = RecipeItemMeasurement.CreateRecipeItemMeasurement(recipeItemMeasurementRequest.getName(),
                recipeItemMeasurementRequest.getConversionRatio(),
                recipeItemMeasurementRequest.getMeasurementName(),
                recipeItemMeasurementRequest.getAbbreviatedMeasurementName());
        recipeItemMeasurementRepository.save(recipeItemMeasurement);

        URI newRecipeItemMeasurementLocation = recipeIteamMeasurementUri(recipeItemMeasurement.getRecipeItemMeasurementId().getId());
        return ResponseEntity.created(newRecipeItemMeasurementLocation).body(recipeItemMeasurement);
    }

    URI recipeIteamMeasurementUri(UUID id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
    }


}
