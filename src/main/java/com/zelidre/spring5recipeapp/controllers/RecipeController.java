package com.zelidre.spring5recipeapp.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.exceptions.NotFoundException;
import com.zelidre.spring5recipeapp.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe/{id}/show") 
	 public String ShowById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		
		return "recipe/show";
	}
	
	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new RecipeCommand());
	
		return RECIPE_RECIPEFORM_URL;
	}
	
	
	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError ->{
				log.debug(objectError.toString());
			});
			return RECIPE_RECIPEFORM_URL;
		}
		
		RecipeCommand saveCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/" + saveCommand.getId() + "/show";
		
		
	}
	

	@RequestMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("deleting id : " +id);
		
		recipeService.deleteById(id);
		
		return "redirect:/";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView hanldeNotFound(Exception exception){
		log.error("Handling Not Found Exception");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404Error");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}
	
}
