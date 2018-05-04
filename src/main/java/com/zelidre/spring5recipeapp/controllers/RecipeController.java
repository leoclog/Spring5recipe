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
		log.debug("Enter Update");
		model.addAttribute("recipe", recipeService.findCommandById(id));
		log.debug("Exit Update");
		return RECIPE_RECIPEFORM_URL;
	}

	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		log.debug("SaveOrUpdate");
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});
			return RECIPE_RECIPEFORM_URL;
		}
		// Categories and Ingredients are not input fields. They will not be passed in
		// the
		// command object. Therefore read the Original reciept and update the Categories
		// and Ingredients
		log.debug("Number of Categories : " + command.getCategories().size());
		log.debug("Number of Ingredients : " + command.getIngredients().size());
		log.debug("Reciept Id :" + command.getId());
		RecipeCommand origCommand = recipeService.findCommandById(command.getId());
		if (origCommand == null) {
			log.debug("origCommand is null");
		} else {

			log.debug("Original Number of Categories : " + origCommand.getCategories().size());
			command.setCategories(origCommand.getCategories());
			log.debug("Original Number of Ingredients : " + origCommand.getIngredients().size());
			command.setIngredients(origCommand.getIngredients());
		}

		// Set the Categories and Ingredients to original values;
		log.debug("Before save Recipe Command");

		RecipeCommand saveCommand = recipeService.saveRecipeCommand(command);

		log.debug("After save Recipe Command");

		return "redirect:/recipe/" + saveCommand.getId() + "/show";

	}

	@RequestMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("deleting id : " + id);

		recipeService.deleteById(id);

		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView hanldeNotFound(Exception exception) {
		log.error("Handling Not Found Exception");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404Error");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}

}
