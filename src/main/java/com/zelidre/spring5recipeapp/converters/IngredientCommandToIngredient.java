package com.zelidre.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.commands.IngredientCommand;
import com.zelidre.spring5recipeapp.domain.Ingredient;

import lombok.Synchronized;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
	
	private final UnitOfMeasureCommandToUnitOfMeasure uomCTuom;
	
	

	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomCTuom) {
		this.uomCTuom = uomCTuom;
	}


	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}
		
		final Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setUom(uomCTuom.convert(source.getUom()));
		
		return ingredient;
		
			
		
	}

}
