package com.zelidre.spring5recipeapp.services;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;

import reactor.core.publisher.Mono;

public interface IngredientService {
	
	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	void deleteById(String recipeId, String ingredientId);

}
