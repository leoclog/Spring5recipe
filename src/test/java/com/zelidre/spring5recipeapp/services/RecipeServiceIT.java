package com.zelidre.spring5recipeapp.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.converters.RecipeCommandToRecipe;
import com.zelidre.spring5recipeapp.converters.RecipeToRecipeCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
	
	public static final String NEW_DESCRIPTION="New Description";

	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeCommandToRecipe rcpCTrcp;
	
	@Autowired
	RecipeToRecipeCommand rcpTrcpC;
	
	@Test
	public void testSaveOfDescription() throws Exception {
		//given
		Iterable<Recipe> recipes = recipeRepository.findAll();
		
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = rcpTrcpC.convert(testRecipe);
		
		//when
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand saveRecipeCommand= recipeService.saveRecipeCommand(testRecipeCommand);
		
		//then
		assertEquals(NEW_DESCRIPTION, saveRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), saveRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), saveRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), saveRecipeCommand.getIngredients().size());
		
	}
}
