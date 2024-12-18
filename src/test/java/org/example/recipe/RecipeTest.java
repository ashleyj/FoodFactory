package org.example.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.example.ConstraintViolationHandler;
import org.example.recipe.dto.RecipeResponse;
import org.example.recipe.dto.RegisterRecipeRequest;
import org.example.recipe.dto.UpdateRecipeRequest;
import org.example.recipe.recipeid.RecipeId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.recipe.RecipeApi.RECIPE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeTest {
    @Autowired
    RecipeApi recipeApi;

    private MockMvc mvc;

    @Test
    public void givenARecipe_whenCreated() {
        RegisterRecipeRequest recipeRequest = new RegisterRecipeRequest("new-recipe", "A new recipe", "a really tasty recipe");
        WebTestClient.ResponseSpec response = recipeApi.registerItem(recipeRequest);
        itShouldRegisterNewRecipe(response);
        RecipeResponse newRecipe = recipeApi.getRecipeFromResponse(response);
        itShouldAllocateAnId(newRecipe);
        itShouldShowWhereToLocateRecipe(response, newRecipe);
    }


    @Test
    public void givenARecipe_whenStepsAdded() {
        RegisterRecipeRequest recipeRequest = new RegisterRecipeRequest("new-recipe", "A new recipe", "a really tasty recipe");
        WebTestClient.ResponseSpec response = recipeApi.registerItem(recipeRequest);
        RecipeResponse newRecipe = recipeApi.getRecipeFromResponse(response);
        AddStepToRecipeRequest stepRequest = new AddStepToRecipeRequest(newRecipe.getRecipeId(), Lists.newArrayList("step text"));
        response = recipeApi.addStepToRecipe(stepRequest);
        RecipeResponse recipeWithSteps = recipeApi.getRecipeFromResponse(response);
        itShouldReturnRecipeWithSteps(recipeWithSteps);
    }


    @Test
    public void givenARecipe_whenFieldsUpdated() {
        RegisterRecipeRequest recipeRequest = new RegisterRecipeRequest("new-recipe", "A new recipe", "a really tasty recipe");
        WebTestClient.ResponseSpec response = recipeApi.registerItem(recipeRequest);
        RecipeResponse newRecipe = recipeApi.getRecipeFromResponse(response);

        UpdateRecipeRequest updateRequest = new UpdateRecipeRequest(newRecipe.getRecipeId(), "updated-recipe", "A new updated recipe", "a really tasty updated recipe");
        response = recipeApi.updateRecipe(updateRequest);
        RecipeResponse updatedRecipe = recipeApi.getRecipeFromResponse(response);
        itShouldContainUpdatedValues(newRecipe, updatedRecipe);
    }

    @Test
    public void givenARecipe_whenFieldsUpdatedWithInvalidContent() throws Exception {
        RegisterRecipeRequest recipeRequest = new RegisterRecipeRequest("new-recipe", "A new recipe", "a really tasty recipe");
        WebTestClient.ResponseSpec response = recipeApi.registerItem(recipeRequest);
        RecipeResponse newRecipe = recipeApi.getRecipeFromResponse(response);

        UpdateRecipeRequest updateRequest = new UpdateRecipeRequest(newRecipe.getRecipeId(), null, "A new updated recipe", "a really tasty updated recipe");
        response = recipeApi.updateRecipe(updateRequest);
        itShouldReturnBadRequest(response);

        updateRequest = new UpdateRecipeRequest(newRecipe.getRecipeId(), "new name", null, "a really tasty updated recipe");
        response = recipeApi.updateRecipe(updateRequest);
        itShouldReturnBadRequest(response);

        updateRequest = new UpdateRecipeRequest(newRecipe.getRecipeId(), "new name", "new title", null);
        response = recipeApi.updateRecipe(updateRequest);
        itShouldReturnBadRequest(response);
        UpdateRecipeRequest requestWithNoName = new UpdateRecipeRequest(newRecipe.getRecipeId(),"", "title", "description");
        UpdateRecipeRequest requestWithNoTitle = new UpdateRecipeRequest(newRecipe.getRecipeId(), "name", "", "description");
        UpdateRecipeRequest requestWithNoDescription = new UpdateRecipeRequest(newRecipe.getRecipeId(), "name", "title", "");
        updateItemPostRequest(newRecipe.getRecipeId(), requestWithNoName, "Name");
        updateItemPostRequest(newRecipe.getRecipeId(), requestWithNoTitle, "Title");
        updateItemPostRequest(newRecipe.getRecipeId(), requestWithNoDescription, "Description");
    }

    private void itShouldReturnBadRequest(WebTestClient.ResponseSpec response) {
        response.expectStatus().isBadRequest();
    }

    private void updateItemPostRequest(UUID recipeId, UpdateRecipeRequest request, String missingField) throws Exception {
        mvc = standaloneSetup(new RecipeController()).setControllerAdvice(new ConstraintViolationHandler()).build();
        mvc.perform(put(RECIPE_PATH + "/" + recipeId).
                        contentType(APPLICATION_JSON)
                        .content(asJSON(request)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(missingField + " is required"));

    }

    private void itShouldContainUpdatedValues(RecipeResponse newRecipe, RecipeResponse updatedRecipe) {
        assertThat(newRecipe.getRecipeId()).isEqualTo(updatedRecipe.getRecipeId());
        assertThat(newRecipe.getTitle()).isNotEqualTo(updatedRecipe.getTitle());
        assertThat(newRecipe.getDescription()).isNotEqualTo(updatedRecipe.getDescription());
        assertThat(newRecipe.getName()).isNotEqualTo(updatedRecipe.getName());
    }

    private void itShouldReturnRecipeWithSteps(RecipeResponse recipeResponse) {
        assertThat(recipeResponse.getRecipeId()).isNotEqualTo(new UUID(0, 0));
        assertThat((long) recipeResponse.getSteps().size()).isEqualTo(1);
        assertThat(recipeResponse.getSteps().getFirst().getStepText()).isEqualTo("step text");
    }

    private void itShouldAllocateAnId(RecipeResponse recipeResponse) {
        assertThat(recipeResponse.getRecipeId()).isNotEqualTo(new UUID(0, 0));
        assertThat(recipeResponse.getRecipeId()).isNotNull();
    }

    private void itShouldRegisterNewRecipe(WebTestClient.ResponseSpec response) {
                response.expectStatus()
                .isCreated();
    }

    private void itShouldShowWhereToLocateRecipe(WebTestClient.ResponseSpec response,RecipeResponse recipeResponse) {
                response.expectHeader()
                .location(recipeApi.uriForItemId(recipeResponse.getRecipeId()).toString());
    }

    @Test
    public void givenBadArguments_whenCreated() throws Exception {

        RegisterRecipeRequest requestWithNoName = new RegisterRecipeRequest("", "title", "description");
        RegisterRecipeRequest requestWithNoTitle = new RegisterRecipeRequest("name", "", "description");
        RegisterRecipeRequest requestWithNoDescription = new RegisterRecipeRequest("name", "title", "");
        newItemPostRequest(requestWithNoName, "Name");
        newItemPostRequest(requestWithNoTitle, "Title");
        newItemPostRequest(requestWithNoDescription, "Description");
    }

    private void newItemPostRequest(RegisterRecipeRequest request, String missingField) throws Exception {
        mvc = standaloneSetup(new RecipeController()).setControllerAdvice(new ConstraintViolationHandler()).build();
        mvc.perform(post(RECIPE_PATH).
                        contentType(APPLICATION_JSON)
                        .content(asJSON(request)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(missingField + " is required"));

    }

    private String asJSON(Object recipeRequest) throws JsonProcessingException {
       return new ObjectMapper().writeValueAsString(recipeRequest);
    }

}
