package org.example.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ConstraintViolationHandler;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.recipe.RecipeApi.RECIPE_PATH;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        RecipeResponse newRecipe = recipeApi.getItemFromResponse(response);
        itShouldAllocateAnId(newRecipe);
        itShouldShowWhereToLocateRecipe(response, newRecipe);
    }

    private void itShouldAllocateAnId(RecipeResponse recipeResponse) {
        assertThat(recipeResponse.getRecipeId().id).isNotEqualTo(new UUID(0, 0));
        assertThat(recipeResponse.getRecipeId().id).isNotNull();
    }

    private void itShouldRegisterNewRecipe(WebTestClient.ResponseSpec response) {
                response.expectStatus()
                .isCreated();
    }

    private void itShouldShowWhereToLocateRecipe(WebTestClient.ResponseSpec response,RecipeResponse recipeResponse) {
                response.expectHeader()
                .location(recipeApi.uriForItemId(recipeResponse.recipeId.id).toString());
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
