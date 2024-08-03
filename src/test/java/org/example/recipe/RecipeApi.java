package org.example.recipe;

import org.example.Helper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.UUID;

@Lazy
@Component
public class RecipeApi {

  public static final String RECIPE_PATH = "/recipes";
    @Value(value="${local.server.port}")
    private int port;

    public URI uriForItemId(UUID id) {
        return URI.create(Helper.baseUri(port) + RECIPE_PATH + "/" + id);
    }

    public WebTestClient.ResponseSpec registerItem(RegisterRecipeRequest recipeRequest) {
        return Helper.newWebClient(port)
                .post()
                .uri(RECIPE_PATH)
                .bodyValue(recipeRequest)
                .exchange();
    }

     public RecipeResponse getRecipeById(UUID itemId) {
        return Helper.newWebClient(port)
                .get()
                .uri(RECIPE_PATH + "/" + itemId)
                .exchange()
                .expectBody(RecipeResponse.class)
                .returnResult()
                .getResponseBody();
    }

    public RecipeResponse getItemFromResponse(WebTestClient.ResponseSpec response) {
        return response
                .expectBody(RecipeResponse.class)
                .returnResult()
                .getResponseBody();
    }
}
