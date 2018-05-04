package com.zelidre.spring5recipeapp.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.exceptions.NotFoundException;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;
import com.zelidre.spring5recipeapp.services.RecipeService;

public class RecipeControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	RecipeController controller;

	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
                				 .setControllerAdvice(new ControllerExceptionHandler())
				                 .build();
	}

	@Test
	public void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		
		
		when(recipeService.findById(anyString())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/1/show"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("recipe/show"))
			   .andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testGetNewRecipeForm() throws Exception{
		RecipeCommand command = new RecipeCommand();
		command.setId("2");
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(get("/recipe/new"))
			            .andExpect(status().isOk())
			            .andExpect(view().name("recipe/recipeform"))
			            .andExpect(model().attributeExists("recipe"));
		
	}
	
	@Test
	public void testPostNewRecipeForm() throws Exception{
		RecipeCommand command = new RecipeCommand();
		command.setId("2");
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", "2")
						.param("description", "some description")
						.param("directions", "some directions")
						)
						.andExpect(status().is3xxRedirection())
						.andExpect(view().name("redirect:/recipe/2/show"));
                        
	}
	
	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");
		
		when(recipeService.findCommandById(anyString())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/update"))
			               .andExpect(status().isOk())
			               .andExpect(view().name("recipe/recipeform"))
			               .andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testDeleteAction() throws Exception{
		mockMvc.perform(get("/recipe/1/delete"))
		                .andExpect(status().is3xxRedirection())
		                .andExpect(view().name("redirect:/"));
		
		verify(recipeService, times(1)).deleteById(anyString());
	}
	
	@Test
	public void getRecipeByidTestNotFound() throws Exception{
		Recipe recipe = new Recipe();
		recipe.setId("2");
		
		when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isNotFound());
	}
	
//	@Test
//	public void getRecipeByidTestNumberFormatException() throws Exception{
//		Recipe recipe = new Recipe();
//		recipe.setId("1");
		
	
//		mockMvc.perform(get("/recipe/leo/show")).andExpect(status().isBadRequest());
//	}

	@Test
	public void testGetRecipeNotFound() throws Exception{
		
		when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show"))
		               .andExpect(status().isNotFound())
		               .andExpect(view().name("404Error"));
	}

	@Test
	public void testGetRecipeBadRequest() throws Exception{
		
		when(recipeService.findById(anyString())).thenThrow(NumberFormatException.class);
		
		mockMvc.perform(get("/recipe/leo/show"))
		               .andExpect(status().isBadRequest())
		               .andExpect(view().name("400Error"));
	}

}
