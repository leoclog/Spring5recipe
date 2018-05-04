package com.zelidre.spring5recipeapp.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.services.ImageService;
import com.zelidre.spring5recipeapp.services.RecipeService;

public class ImageControllerTest {
	
	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeService;
	
	ImageController imageController;
	
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ImageController controller= new ImageController(imageService, recipeService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				                 .setControllerAdvice(new ControllerExceptionHandler())
				                 .build();
		

	}

	@Test
	public void testshowUploadForm() throws Exception {
		
		RecipeCommand recipeCommand = new RecipeCommand();
		
		recipeCommand.setId("1");
		
		when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/1/image"))
						.andExpect(status().isOk())
						.andExpect(view().name("recipe/imageuploadform"))
						.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService, times(1)).findCommandById(anyString());
	}

	@Test
	public void testhandleImagePost() throws Exception{
		MockMultipartFile multipartFile = 
				new MockMultipartFile("imagefile", "testing.txt", "text/plain", 
									  "Zelidre Mangement dot com".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                                  .andExpect(status().is3xxRedirection())
                                  .andExpect(view().name("redirect:/recipe/1/show"));
		
		verify(imageService, times(1)).saveImageFile(anyString(), any());
	}

	@Test
	public void renderImageFormDB() throws Exception {
		RecipeCommand command = new RecipeCommand();
		
		command.setId("1");
		
		String s = "Fake image test";
		Byte[] bytes = new Byte[s.getBytes().length];
		
		int i=0;
		
		for (byte b: s.getBytes()) {
			bytes[i++]=b;
		}
		command.setImage(bytes);
		
		when(recipeService.findCommandById(anyString())).thenReturn(command);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				                           .andExpect(status().isOk())
				                           .andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		assertEquals(s.getBytes().length, responseBytes.length);
		
	}
	
//	@Test
//	public void imageNumberFormatException() throws Exception {
		
//		mockMvc.perform(get("/recipe/leo/recipeimage"))
//		                .andExpect(status().isBadRequest())
//		                .andExpect(view().name("400Error"));
//	}
}