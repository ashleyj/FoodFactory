package org.example.recipeitem.recipeitemmeasurement;

import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeItemMeasurementTest {

    @Autowired
    RecipeItemMeasurementApi recipeItemMeasurementApi;


    public WebTestClient.ResponseSpec generateRecipeItemMeasurement() {
        RecipeItemMeasurement recipeItemMeasurement = RecipeItemMeasurement.CreateRecipeItemMeasurement("test2", 1,
                "litre", "ltr");

        RegisterRecipeItemMeasurementRequest recipeItemMeasurementRequest = new RegisterRecipeItemMeasurementRequest(recipeItemMeasurement.getName(),
                recipeItemMeasurement.getRecipeItemMeasurementId(),
                recipeItemMeasurement.getConversionRatio(), recipeItemMeasurement.getMeasurementName(), recipeItemMeasurement.getAbbreviatedMeasurementName());
        WebTestClient.ResponseSpec response = recipeItemMeasurementApi.registerRecipeItemMeasurement(recipeItemMeasurementRequest);

        return response;
    }

    @Test
    public void givenARecipeItemMeasurement_whenCreated() {
        WebTestClient.ResponseSpec response = generateRecipeItemMeasurement();
        RecipeItemMeasurementResponse newItem = recipeItemMeasurementApi.getItemFromResponse(response);
        this.itShouldAllocateAnId(newItem);
        this.itShouldKnowWhereToLocateRecipeMeasurement(response, newItem);

    }

    @Test
    public void givenARecipeItemMeasurement_whenDuplicateCreated() {

        // first call
        WebTestClient.ResponseSpec response = generateRecipeItemMeasurement();
        RecipeItemMeasurementResponse newItem = recipeItemMeasurementApi.getItemFromResponse(response);
        this.itShouldAllocateAnId(newItem);

        // second call
        response = generateRecipeItemMeasurement();
        itShouldNotAllowADuplicateName(response);

    }


    private void itShouldAllocateAnId(RecipeItemMeasurementResponse response) {
        assertThat(response.recipeItemMeasurementId.getRecipe_item_measurement_d()).isNotEqualTo(new UUID(0, 0));
        assertThat(response.recipeItemMeasurementId.getRecipe_item_measurement_d()).isNotNull();
    }

    private void itShouldKnowWhereToLocateRecipeMeasurement(WebTestClient.ResponseSpec response, RecipeItemMeasurementResponse recipeItemMeasurementResponse) {
        response.expectHeader()
                .location(recipeItemMeasurementApi.uriForRecipeItemMeasurement(recipeItemMeasurementResponse.getRecipeItemMeasurementId().getRecipe_item_measurement_d()).toString());
    }


    private void itShouldNotAllowADuplicateName(WebTestClient.ResponseSpec response) {
        response.expectStatus().isBadRequest();
    }

}
