package com.zelidre.spring5recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;
import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.converters.IngredientCommandToIngredient;
import com.zelidre.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.zelidre.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.zelidre.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;
import com.zelidre.spring5recipeapp.domain.Ingredient;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;
import com.zelidre.spring5recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IngredientServiceImplTest {
	private final IngredientToIngredientCommand ingTingC;
	private final IngredientCommandToIngredient ingCTing;

	IngredientService ingredientService;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	UnitOfMeasureRepository uomRepository;

	public IngredientServiceImplTest() {
		this.ingTingC = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingCTing = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ingredientService = new IngredientServiceImpl(ingTingC, ingCTing, recipeRepository, uomRepository);
	}

	@Test
	public void FindRecipeIdandId() throws Exception {

	}

	@Test
	public void findByRecipeIdAndRecipeHappyPath() {
		// given

		Recipe recipe = new Recipe();
		recipe.setId("1");

		Ingredient ing1 = new Ingredient();
		Ingredient ing2 = new Ingredient();
		Ingredient ing3 = new Ingredient();
		ing1.setId("1");
		ing2.setId("2");
		ing3.setId("3");

		recipe.addIngredient(ing2);
		recipe.addIngredient(ing1);
		recipe.addIngredient(ing3);

		Optional<Recipe> recipeOptional = Optional.of(recipe);

		// when

		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

		// then

		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3");

		assertEquals("3", ingredientCommand.getId());
		verify(recipeRepository, times(1)).findById(anyString());

	}

	@Test
	public void testDeleteById() throws Exception {

		Recipe recipe = new Recipe();
		recipe.setId("1");

		Ingredient ing1 = new Ingredient();
		Ingredient ing2 = new Ingredient();
		Ingredient ing3 = new Ingredient();
		ing1.setId("1");
		ing2.setId("2");
		ing3.setId("3");

		recipe.addIngredient(ing2);
		recipe.addIngredient(ing1);
		recipe.addIngredient(ing3);

		Optional<Recipe> recipeOptional = Optional.of(recipe);

		// when

		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

		// then

		ingredientService.deleteById("1", "2");

		assertEquals(2, recipe.getIngredients().size());
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, times(1)).save(any(Recipe.class));

	}

	@Test
	public void testsaveIngredientComment() throws Exception {
		
		IngredientCommand ingC = new IngredientCommand();
		ingC.setId("3");
		ingC.setRecipeId("2");
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId("3");
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		IngredientCommand recipeCommand = ingredientService.saveIngredientCommand(ingC);
		
		assertEquals("3", recipeCommand.getId());
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}

}