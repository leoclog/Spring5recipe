package com.zelidre.spring5recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zelidre.spring5recipeapp.converters.RecipeCommandToRecipe;
import com.zelidre.spring5recipeapp.converters.RecipeToRecipeCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.exceptions.NotFoundException;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		recipeService = new RecipeServiceImpl(recipeRepository, 
				                              recipeCommandToRecipe, 
				                              recipeToRecipeCommand);
	}
	@Test
	public void getRecipeByIdTest() throws Exception{
		Recipe recipe = new Recipe();
		recipe.setId("1");
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		Recipe recipeReturned = recipeService.findById("1");
		
		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, never()).findAll();
		
		
	}

	@Test
	public void testGetRecipes() {
		Recipe recipe = new Recipe();
		HashSet<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);
		
		when(recipeService.getRecipes()).thenReturn(recipesData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
		verify(recipeRepository, never()).findById(anyString());
	}
	
	@Test 
	public void testDeletedById() throws Exception{
		
		recipeService.deleteById("2");
		
		//no when, since method has void return type
		
		verify(recipeRepository, times(1)).deleteById(anyString());
	}
	
	@Test(expected = NotFoundException.class)
	public void getRecipeByidTestNotFound() throws Exception{
		Optional<Recipe> recipeOptional = Optional.empty();
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		Recipe recipe = recipeService.findById("1");

	}
}
