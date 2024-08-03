package org.example.recipeitem.recipeitemmeasurement;

import org.example.Helper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.UUID;

@Lazy
@Component
public class RecipeItemMeasurementApi {
    private static final String RECIPEITEMMEASUREMENT_PATH = "/recipeitemmeasurements";
    @Value(value = "${local.server.port}")
    private int port;


    public WebTestClient.ResponseSpec registerRecipeItemMeasurement(RegisterRecipeItemMeasurementRequest recipeItemMeasurementRequest) {
        return Helper.newWebClient(port)
                .post()
                .uri(RECIPEITEMMEASUREMENT_PATH)
                .bodyValue(recipeItemMeasurementRequest)
                .exchange();
    }

    public RecipeItemMeasurementResponse getItemFromResponse(WebTestClient.ResponseSpec response) {
        return response
                .expectBody(RecipeItemMeasurementResponse.class)
                .returnResult()
                .getResponseBody();
    }


    public URI uriForRecipeItemMeasurement(UUID id) {
        return URI.create(Helper.baseUri(port) + RECIPEITEMMEASUREMENT_PATH + "/" + id);
    }
}
