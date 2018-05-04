package com.zelidre.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{
	private final IngredientToIngredientCommand ingTingC;
	private final CategoryToCategoryCommand catTcatC;
	private final NotesToNotesCommand notTnotC;

	public RecipeToRecipeCommand(IngredientToIngredientCommand ingTingC, CategoryToCategoryCommand catTcatC,
			NotesToNotesCommand notTnotC) {
		this.ingTingC = ingTingC;
		this.catTcatC = catTcatC;
		this.notTnotC = notTnotC;
	}
	
	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		log.debug("Recipe to RecipeCommand Conversion");
		if (source == null) {
			return null;
		}
		
		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setNotes(notTnotC.convert(source.getNotes()));
		
		if (source.getCategories() != null &&
				source.getCategories().size()>0) {
			source.getCategories()
			   .forEach(category -> recipeCommand.getCategories().add(catTcatC.convert(category)));
		}
		if (source.getIngredients() != null &&
				source.getIngredients().size()>0) {
			source.getIngredients()
			   .forEach(ingredient -> recipeCommand.getIngredients().add(ingTingC.convert(ingredient)));
		}

		log.debug("end of RecipetoRecipeCommand Conversion");
		return recipeCommand;
	}



}
