package org.example.recipeitem;

import org.example.item.*;
import org.example.item.itemid.ItemId;
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
    public ItemApi itemApi;

    @Test
    public void givenARecipeItem_whenCreated() {

        RegisterItemRequest itemRequest = new RegisterItemRequest("new item");
        WebTestClient.ResponseSpec response = itemApi.registerItem(itemRequest);
        ItemResponse item = itemApi.getItemFromResponse(response);

        RegisterRecipeItemRequest recipeItemRequest = new RegisterRecipeItemRequest(item.itemId);
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
        RegisterRecipeItemRequest itemRequest = new RegisterRecipeItemRequest(itemId);
        WebTestClient.ResponseSpec response = recipeItemApi.registerRecipeItem(itemRequest);
        RecipeItemResponse newItem = recipeItemApi.getItemFromResponse(response);
        itShouldReturnAnInvalidItemError(response);
    }

    private void itShouldReturnAnInvalidItemError(WebTestClient.ResponseSpec response) {
        response.expectStatus().isBadRequest();
    }
}
