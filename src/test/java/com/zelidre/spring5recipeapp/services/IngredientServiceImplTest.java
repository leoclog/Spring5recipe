package com.zelidre.spring5recipeapp.services;


import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;
import com.zelidre.spring5recipeapp.converters.IngredientCommandToIngredient;
import com.zelidre.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.zelidre.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.zelidre.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.domain.Ingredient;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;
import com.zelidre.spring5recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IngredientServiceImplTest {
	private final IngredientToIngredientCommand ingTingC;
	private final IngredientCommandToIngredient ingCTing;

	
	IngredientServiceImpl ingredientService;
	
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
		
		ingredientService = new IngredientServiceImpl(ingTingC,
				ingCTing, recipeRepository, uomRepository);
	}

	@Test
	public void FindRecipeIdandId() throws Exception{
		
	}

	@Test
	public void findByRecipeIdAndRecipeHappyPath() {
		//given
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ing1 = new Ingredient();
		Ingredient ing2 = new Ingredient();
		Ingredient ing3 = new Ingredient();
		ing1.setId(1L);
		ing2.setId(2L);
		ing3.setId(3L);
		
		recipe.addIngredient(ing2);
		recipe.addIngredient(ing1);
		recipe.addIngredient(ing3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		
		
		//when
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//then
		
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		

		
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
		
	}
	
	@Test
	public void testDeleteById() {
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ing1 = new Ingredient();
		Ingredient ing2 = new Ingredient();
		Ingredient ing3 = new Ingredient();
		ing1.setId(1L);
		ing2.setId(2L);
		ing3.setId(3L);
		
		recipe.addIngredient(ing2);
		recipe.addIngredient(ing1);
		recipe.addIngredient(ing3);
		
		
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		
		
		//when
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//then 
		
		ingredientService.deleteById(1L, 2L);
		
		assertEquals(2, recipe.getIngredients().size());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	
	}
}
