package com.zelidre.spring5recipeapp.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;

public class ImageServiceImplTest  {
	ImageService imageService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	public void saveImageFile() throws Exception{
		//given
		MultipartFile multiPartFile = new MockMultipartFile("imagefile", 
				                                            "testing.txt", 
				                                            "plain/txt", 
				                                            "com Zelidre".getBytes());
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		//when
		imageService.saveImageFile("1", multiPartFile);
		
		//then
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe saveRecipe = argumentCaptor.getValue();
		assertEquals(multiPartFile.getBytes().length, saveRecipe.getImage().length);
	}
}
