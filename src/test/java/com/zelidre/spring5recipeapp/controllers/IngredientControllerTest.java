package com.zelidre.spring5recipeapp.controllers;





import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;
import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;
import com.zelidre.spring5recipeapp.services.IngredientService;
import com.zelidre.spring5recipeapp.services.RecipeService;
import com.zelidre.spring5recipeapp.services.UnitOfMeasureService;

import reactor.core.publisher.Flux;

public class IngredientControllerTest {

	@Mock
	IngredientService ingredientService;
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock 
	UnitOfMeasureService uomService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		controller = new IngredientController(ingredientService, recipeService, uomService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
	}
	
	@Test 
	public void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
	
		when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/1/ingredients"))
		                .andExpect(status().isOk())
		                .andExpect(view().name("recipe/ingredient/list"))
		                .andExpect(model().attributeExists("recipe"));
		
		verify(recipeService, times(1)).findCommandById(anyString());
	}
	
	@Test
	public void testShowIngredient() throws Exception{
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand);
		
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		                .andExpect(status().isOk())
		                .andExpect(view().name("recipe/ingredient/show"))
		                .andExpect(model().attributeExists("ingredient"));
		                
		                
	}
	
	@Test
	public void testupdateRecipeIngredient() throws Exception{
		//given
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand);
		when(uomService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
		                .andExpect(status().isOk())
		                .andExpect(view().name("recipe/ingredient/ingredientform"))
		                .andExpect(model().attributeExists("ingredient"))
		                .andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	public void testsaveOrUpdate() throws Exception{
		//given
		IngredientCommand ingC = new IngredientCommand();
		ingC.setId("3");
		ingC.setRecipeId("2");
		
		when(ingredientService.saveIngredientCommand(any())).thenReturn(ingC);
		
		mockMvc.perform(post("/recipe/2/ingredient"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	}
	
	@Test
	public void testdeleteById() throws Exception{
		
		mockMvc.perform(get("/recipe/5/ingredient/2/delete"))
		                .andExpect(status().is3xxRedirection())
		                .andExpect(view().name("redirect:/recipe/5/ingredients"));
		
		verify(ingredientService, times(1)).deleteById(anyString(), anyString());
	                         
		
	}
	
}                      