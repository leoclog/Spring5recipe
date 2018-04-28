package com.zelidre.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.domain.Notes;
import com.zelidre.spring5recipeapp.domain.Recipe;

import lombok.Synchronized;
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>{
	
	private final IngredientCommandToIngredient ingCTing;
	private final CategoryCommandToCategory catCTcat;
	private final NotesCommandToNotes notCTnot;
	

	public RecipeCommandToRecipe(IngredientCommandToIngredient ingCTing, CategoryCommandToCategory catCTcat,
			NotesCommandToNotes notCTnot) {
		this.ingCTing = ingCTing;
		this.catCTcat = catCTcat;
		this.notCTnot = notCTnot;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}
		
		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setCookTime(source.getCookTime());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setDescription(source.getDescription());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setImage(source.getImage());
		recipe.setNotes(notCTnot.convert(source.getNotes()));
		
		if (source.getCategories() != null &&
				source.getCategories().size()>0) {
			source.getCategories()
			   .forEach(category -> recipe.getCategories().add(catCTcat.convert(category)));
		}
		if (source.getIngredients() != null &&
				source.getIngredients().size()>0) {
			source.getIngredients()
			   .forEach(ingredient -> recipe.getIngredients().add(ingCTing.convert(ingredient)));
	 	}

		
		return recipe;
	}

}
