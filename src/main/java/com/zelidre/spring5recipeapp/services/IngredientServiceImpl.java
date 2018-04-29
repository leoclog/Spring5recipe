package com.zelidre.spring5recipeapp.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;
import com.zelidre.spring5recipeapp.converters.IngredientCommandToIngredient;
import com.zelidre.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.zelidre.spring5recipeapp.domain.Ingredient;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;
import com.zelidre.spring5recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	private final IngredientToIngredientCommand ingTingC;
	private final IngredientCommandToIngredient ingCTing;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository uomRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingTingC, IngredientCommandToIngredient ingCTing,
			RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository) {
		this.ingTingC = ingTingC;
		this.ingCTing = ingCTing;
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
	}


	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
	

		if (!recipeOptional.isPresent()) {
			//todo impl error handling
			log.error("recipe id is not found. Id: " + recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		//Optional<IngredientCommand> ingredientCommandOptional  
		//		  recipe.getIngredients().stream()
		//		                         .filter(ingredient -> ingredient.getId().equals(ingredientId))
		//		                         .map(ingredient-> ingTingC.convert(ingredient)).findFirst();
		
		Optional<Ingredient> ing = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
		
		if(!ing.isPresent()) {
			//todo impl error handling
			log.error("Ingredient id not found: " + ingredientId);
		}
		
				
				 
		return ingTingC.convert(ing.get());
				                                             
	}


	@Transactional
	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		
		if(!recipeOptional.isPresent()) {
			//toto error
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient>  
		   ingredientOptional = recipe.getIngredients()
		                              .stream()
		                              .filter(ingredient -> ingredient.getId().equals(command.getId()))
		                              .findFirst();
		
		if (ingredientOptional.isPresent()) {
			Ingredient ingredientFound = ingredientOptional.get();
			log.debug("IngredientFound : " + ingredientFound.getDescription());
			ingredientFound.setDescription(command.getDescription());
			ingredientFound.setAmount(command.getAmount());
			ingredientFound.setUom(uomRepository
					               .findById(command.getUom().getId())
					               .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
		}
		else
		{
			//add newIngredient
			log.debug("Ingredient not found!");
			recipe.addIngredient(ingCTing.convert(command));
		}
							 

		Recipe savedRecipe = recipeRepository.save(recipe);
		
		return ingTingC.convert(savedRecipe.getIngredients()
				                           .stream()
				                           .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
				                           .findFirst()
				                           .get());
				                 
	}


	@Transactional
	@Override
	public void deleteById(String recipeId, String ingredientId) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		log.debug("Start deleting ingredient....");
		if (!recipeOptional.isPresent()) {
			log.error("Recipe not found id : " + recipeId);
			return;
		}
		
			
		Optional<Ingredient> ingredientOptional = recipeOptional.get().getIngredients()
								   .stream()
								   .filter(ingredient -> ingredient.getId().equals(ingredientId))
								   .findFirst();
								   
		if (!ingredientOptional.isPresent()) {
			log.error("Ingredient not found Recipeid : " + recipeId + " ingredientId : " + ingredientId);
			return;
		}
		
		Ingredient ingredient = ingredientOptional.get();
//		ingredient.setRecipe(null);
		
		Recipe recipe = recipeOptional.get();
		
		log.debug("# Ingredients before : " + recipe.getIngredients().size());
		
		recipe.getIngredients().remove(ingredient);
				
		recipeRepository.save(recipe);
		
		log.debug("# Ingredients after : " + recipe.getIngredients().size());
		
		
		
		
		
	}

}
