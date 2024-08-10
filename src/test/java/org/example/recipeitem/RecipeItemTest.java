package org.example.recipeitem;

import org.example.item.*;
import org.example.item.itemid.ItemId;
import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.example.recipeitem.measurement.recipeitemmeasurementid.RecipeItemMeasurementId;
import org.example.recipeitem.recipeitemmeasurement.RecipeItemMeasurementApi;
import org.example.recipeitem.recipeitemmeasurement.RecipeItemMeasurementResponse;
import org.example.recipeitem.recipeitemmeasurement.RegisterRecipeItemMeasurementRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeItemTest {

    @Autowired
    public RecipeItemApi recipeItemApi;

    @Autowired
    RecipeItemMeasurementApi recipeItemMeasurementApi;

    @Autowired
    public ItemApi itemApi;

    public RecipeItemMeasurementId generateRecipeItemMeasurementId() {
        RecipeItemMeasurement recipeItemMeasurement = RecipeItemMeasurement.CreateRecipeItemMeasurement("test", 1,
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

        RegisterRecipeItemRequest recipeItemRequest = new RegisterRecipeItemRequest(item.itemId, 3, generateRecipeItemMeasurementId());
        // register recipe item, using existing item
        response = recipeItemApi.registerRecipeItem(recipeItemRequest);
        RecipeItemResponse newItem = recipeItemApi.getItemFromResponse(response);
        itShouldRegisterNewItem(response);
        itShouldAllocateAnId(newItem);
    }

    private void itShouldAllocateAnId(RecipeItemResponse response) {
        assertThat(response.recipeItemId.id).isNotEqualTo(new UUID(0, 0));
        assertThat(response.recipeItemId.id).isNotNull();
    }

    private void itShouldRegisterNewItem(WebTestClient.ResponseSpec response) {
        response.expectStatus()
                .isCreated();
    }

    @Test
    public void givenAnInvalidItem_whenCreated() {
        ItemId itemId = ItemId.generateId();
        RegisterRecipeItemRequest itemRequest = new RegisterRecipeItemRequest(itemId, 3, generateRecipeItemMeasurementId());
        WebTestClient.ResponseSpec response = recipeItemApi.registerRecipeItem(itemRequest);
        RecipeItemResponse newItem = recipeItemApi.getItemFromResponse(response);
        itShouldReturnAnInvalidItemError(response);
    }

    private void itShouldReturnAnInvalidItemError(WebTestClient.ResponseSpec response) {
        response.expectStatus().isBadRequest();
    }
}
