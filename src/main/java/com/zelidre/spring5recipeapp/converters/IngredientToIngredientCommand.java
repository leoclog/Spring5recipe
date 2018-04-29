package com.zelidre.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;
import com.zelidre.spring5recipeapp.domain.Ingredient;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand>{

	private final UnitOfMeasureToUnitOfMeasureCommand uomTuomC;
	
	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomTuomC) {
		this.uomTuomC = uomTuomC;
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		
		if (source == null) {
			return null;
		}
		
		final IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(source.getId());
		ingredientCommand.setAmount(source.getAmount());
		ingredientCommand.setUom(uomTuomC.convert(source.getUom()));
		ingredientCommand.setDescription(source.getDescription());
//		if (source.getRecipe() != null) {
//			ingredientCommand.setRecipeId(source.getRecipe().getId());
//		}
			
		
		return ingredientCommand;
	}

}
