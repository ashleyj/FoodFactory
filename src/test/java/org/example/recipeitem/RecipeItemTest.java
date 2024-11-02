package org.example.recipeitem;

import org.example.item.*;
import org.example.item.dto.ItemResponse;
import org.example.item.dto.RegisterItemRequest;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.dto.RecipeItemResponse;
import org.example.recipeitem.dto.RegisterRecipeItemRequest;
import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.example.recipeitem.measurement.dto.RecipeItemMeasurementResponse;
import org.example.recipeitem.measurement.dto.RegisterRecipeItemMeasurementRequest;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
import org.example.recipeitem.recipeitemmeasurement.RecipeItemMeasurementApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeItemTest {

    @Autowired
    public RecipeItemApi recipeItemApi;

    @Autowired
    RecipeItemMeasurementApi recipeItemMeasurementApi;

    @Autowired
    public ItemApi itemApi;

    public RecipeItemMeasurementId generateRecipeItemMeasurementId() {
        RecipeItemMeasurement recipeItemMeasurement = RecipeItemMeasurement.CreateRecipeItemMeasurement("test3", 1,
                "litre", "ltr");

        RegisterRecipeItemMeasurementRequest recipeItemMeasurementRequest = new RegisterRecipeItemMeasurementRequest(recipeItemMeasurement.getName(),
                recipeItemMeasurement.getRecipeItemMeasurementId(),
                recipeItemMeasurement.getConversionRatio(), recipeItemMeasurement.getMeasurementName(), recipeItemMeasurement.getAbbreviatedMeasurementName());
        WebTestClient.ResponseSpec response = recipeItemMeasurementApi.registerRecipeItemMeasurement(recipeItemMeasurementRequest);
        RecipeItemMeasurementResponse newItem = recipeItemMeasurementApi.getItemFromResponse(response);

        return newItem.getRecipeItemMeasurementId();
    }

    @Test
    public void givenARecipeItem_whenCreated() {

        RegisterItemRequest itemRequest = new RegisterItemRequest("new item");
        WebTestClient.ResponseSpec response = itemApi.registerItem(itemRequest);
        ItemResponse item = itemApi.getItemFromResponse(response);
        RecipeItemMeasurementId recipeItemMeasurementId = generateRecipeItemMeasurementId();

        RegisterRecipeItemRequest recipeItemRequest = new RegisterRecipeItemRequest(item.getItemId().getId(), 3, recipeItemMeasurementId.getRecipe_item_measurement_d());
        // register recipe item, using existing item
        response = recipeItemApi.registerRecipeItem(recipeItemRequest);
        RecipeItemResponse newItem = recipeItemApi.getItemFromResponse(response);
        itShouldRegisterNewItem(response);
        itShouldAllocateAnId(newItem);
    }

    private void itShouldAllocateAnId(RecipeItemResponse response) {
        assertThat(response.recipeItemId.getId()).isNotEqualTo(new UUID(0, 0));
        assertThat(response.recipeItemId.getId()).isNotNull();
    }

    private void itShouldRegisterNewItem(WebTestClient.ResponseSpec response) {
        response.expectStatus()
                .isCreated();
    }

    @Test
    public void givenAnInvalidItem_whenCreated() {
        ItemId itemId = ItemId.generateId();
        RegisterRecipeItemRequest itemRequest = new RegisterRecipeItemRequest(itemId.getId(), 3, generateRecipeItemMeasurementId().getRecipe_item_measurement_d());
        WebTestClient.ResponseSpec response = recipeItemApi.registerRecipeItem(itemRequest);
        RecipeItemResponse newItem = recipeItemApi.getItemFromResponse(response);
        itShouldReturnAnInvalidItemError(response);
    }

    private void itShouldReturnAnInvalidItemError(WebTestClient.ResponseSpec response) {
        response.expectStatus().isBadRequest();
    }
}
