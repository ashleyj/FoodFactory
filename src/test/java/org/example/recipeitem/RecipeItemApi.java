package org.example.recipeitem;

import org.example.Helper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.UUID;

@Lazy
@Component
public class RecipeItemApi {
    private static final String RECIPEITEM_PATH = "/recipeitems";
    @Value(value="${local.server.port}")
    private int port;

    public URI uriForRecipeItem(UUID id) {
        return URI.create(Helper.baseUri(port) + RECIPEITEM_PATH + "/" + id);
    }

    public WebTestClient.ResponseSpec registerRecipeItem(RegisterRecipeItemRequest recipeItemRequest) {
        return Helper.newWebClient(port)
                .post()
                .uri(RECIPEITEM_PATH)
                .bodyValue(recipeItemRequest)
                .exchange();
    }

    public RecipeItemResponse getItemFromResponse(WebTestClient.ResponseSpec response) {
        return response
                .expectBody(RecipeItemResponse.class)
                .returnResult()
                .getResponseBody();
    }
}
