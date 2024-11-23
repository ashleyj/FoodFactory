package org.example.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.example.ConstraintViolationHandler;
import org.example.recipe.dto.RecipeResponse;
import org.example.recipe.dto.RegisterRecipeRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.recipe.RecipeApi.RECIPE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeTest {
    @Autowired
    RecipeApi recipeApi;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


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
        postRequest(requestWithNoName, "Name");
        postRequest(requestWithNoTitle, "Title");
        postRequest(requestWithNoDescription, "Description");
    }

    private void postRequest(RegisterRecipeRequest request, String missingField) throws Exception {
        mvc = standaloneSetup(new RecipeController()).setControllerAdvice(new ConstraintViolationHandler()).build();
        mvc.perform(post(RECIPE_PATH).
                        contentType(APPLICATION_JSON)
                        .content(asJSON(request)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(missingField + " is required"));

    }

    private String asJSON(RegisterRecipeRequest recipeRequest) throws JsonProcessingException {
       return new ObjectMapper().writeValueAsString(recipeRequest);
    }

}
