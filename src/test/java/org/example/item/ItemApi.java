package org.example.item;

import org.example.Helper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.UUID;

@Lazy
@Component
public class ItemApi {
    private static final String ITEM_PATH = "/items";
    @Value(value="${local.server.port}")
    private int port;

    public URI uriForItemId(UUID id) {
        return URI.create(Helper.baseUri(port) + ITEM_PATH + "/" + id);
    }

    public WebTestClient.ResponseSpec registerItem(RegisterItemRequest itemRequest) {
        return Helper.newWebClient(port)
                .post()
                .uri(ITEM_PATH)
                .bodyValue(itemRequest)
                .exchange();
    }

     public ItemResponse getItemById(UUID itemId) {
        return Helper.newWebClient(port)
                .get()
                .uri(ITEM_PATH + "/" + itemId)
                .exchange()
                .expectBody(ItemResponse.class)
                .returnResult()
                .getResponseBody();
    }

    public ItemResponse getItemFromResponse(WebTestClient.ResponseSpec response) {
        return response
                .expectBody(ItemResponse.class)
                .returnResult()
                .getResponseBody();
    }
}
