package org.example.recipeitem;

import org.example.item.Item;
import org.example.item.ItemRepository;
import org.example.recipe.Recipe;
import org.example.recipe.RecipeItemDto;
import org.example.recipe.RecipeRepository;
import org.example.recipeitem.measurement.RecipeItemMeasurement;
import org.example.recipeitem.measurement.RecipeItemMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class RecipeItemServiceTest {

    @Autowired
    RecipeItemService recipeItemService;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private RecipeItemMeasurementRepository recipeItemMeasurementRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    private Item item;
    private RecipeItemMeasurement recipeItemMeasurement;
    private RecipeItem recipeItem;
    private Recipe recipe;

    @BeforeEach
    public void setup() {
        this.recipe = Recipe.createRecipe("New Recipe", "New Recipe", "A New Recipe");
        recipeRepository.save(recipe);

        this.item = Item.craeteItem("New Item");
        itemRepository.save(item);

        this.recipeItemMeasurement = RecipeItemMeasurement.CreateRecipeItemMeasurement("kg", 1, "Kilogram", "kg");
        recipeItemMeasurementRepository.save(recipeItemMeasurement);

    }

    @Test
    public void givenAnExistingRecipe_whenAnItemIsAdded() {
        RecipeItemDto recipeItemDto = new RecipeItemDto();
        recipeItemDto.setRecipeItemMeasurementId(recipeItemMeasurement.getId());
        recipeItemDto.setItemId(item.getItemId().getId());
        recipeItemDto.setCount(6);
        Recipe updatedRecipe = recipeItemService.addRecipeItemsToRecipe(this.recipe.getRecipeId().getId(), List.of(recipeItemDto));
        itShouldAddNewRecipeItem(updatedRecipe);
    }

    private void itShouldAddNewRecipeItem(Recipe updatedRecipe) {
        assertThat(updatedRecipe.getRecipeItems().size()).isEqualTo(1);
        assertThat(updatedRecipe.getRecipeItems().getFirst().getItemId().getId()).isEqualTo(this.item.getItemId().getId());
    }
}
