package org.example.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemTest {
    @Autowired
    private ItemApi itemApi;

    @Test
    public void givenAnItem_whenCreated() {
        RegisterItemRequest itemRequest = new RegisterItemRequest("A new item");
        WebTestClient.ResponseSpec response = itemApi.registerItem(itemRequest);
        itShouldRegisterNewItem(response);
        ItemResponse newItem = itemApi.getItemFromResponse(response);
        itShouldAllocateAnId(newItem);
        itShouldShowWhereToLocateItem(response, newItem);
    }


    private void itShouldShowWhereToLocateItem(WebTestClient.ResponseSpec response, ItemResponse itemResponse) {
        response.expectHeader()
                .location(itemApi.uriForItemId(itemResponse.itemId.id).toString());
    }

    private void itShouldAllocateAnId(ItemResponse itemResponse) {
        assertThat(itemResponse.getId()).isNotEqualTo(new UUID(0, 0));
        assertThat(itemResponse.getId()).isNotNull();
    }

    private void itShouldRegisterNewItem(WebTestClient.ResponseSpec response) {
        response.expectStatus()
                .isCreated();
    }
}
