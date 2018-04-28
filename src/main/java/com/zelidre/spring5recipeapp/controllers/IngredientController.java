package com.zelidre.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;
import com.zelidre.spring5recipeapp.services.IngredientService;
import com.zelidre.spring5recipeapp.services.RecipeService;
import com.zelidre.spring5recipeapp.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;

  
  

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService uomService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}


    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }
    

    @RequestMapping("recipe/{recipeid}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeid,	
      								   @PathVariable String id,
    								   Model model) {
    	model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeid), Long.valueOf(id)));
    	return "recipe/ingredient/show";
    }
    
 
    @RequestMapping("recipe/{recipeid}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeid,
    		                             @PathVariable String id,
    		                             Model model) {
    	model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeid), Long.valueOf(id)));
    	model.addAttribute("uomList", uomService.listAllUoms());
    	
    	return "recipe/ingredient/ingredientform";
    			
    	
    }
    
 
    @RequestMapping("recipe/{recipeid}/ingredient/{id}/delete")
    public String deleteById(@PathVariable String recipeid,
    		                 @PathVariable String id) {
    
    	ingredientService.deleteById(Long.valueOf(recipeid), Long.valueOf(id));
   	
    	log.debug("Deleted Ingredient: " + id + " from recipe: " + recipeid );
    	return "redirect:/recipe/" + recipeid + "/ingredients";
    }
    
    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
    	
    	IngredientCommand ic = ingredientService.saveIngredientCommand(command);
    	
    	
    	log.debug("Saved recipe id : " + ic.getRecipeId());
    	log.debug("Saved Ingredient Id : " + ic.getId());
    	
    	return "redirect:/recipe/" + ic.getRecipeId() + "/ingredients/" + ic.getId() + "/show";
    }
}