/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.services;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.converters.RecipeCommandToRecipe;
import com.zelidre.spring5recipeapp.converters.RecipeToRecipeCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.exceptions.NotFoundException;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lpaalva
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{
    
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    
    
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
    public Set<Recipe> getRecipes() {
    	log.debug("I'm a service");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }
    
    @Override
    public Recipe findById(String l) {
    	Optional<Recipe> recipeOptional = recipeRepository.findById(l);
    	
    	if (!recipeOptional.isPresent()) {
//    		throw new RuntimeException("Recipe Not Found");
    		throw new NotFoundException("Recipe Not Found for Id: " + l.toString());
    	}
    	return recipeOptional.get();
    }
    
    @Transactional
    @Override
    public RecipeCommand findCommandById(String l) {
    	return recipeToRecipeCommand.convert(findById(l)) ;
    	
    	
    }
    
    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
    	Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
	
    	Recipe saveRecipe = recipeRepository.save(detachedRecipe);
    	log.debug("Saved Recipe Id : " + saveRecipe.getId());
    	return recipeToRecipeCommand.convert(saveRecipe);
    }
    
    @Transactional
    @Override
    public void deleteById(String id) {
    	recipeRepository.deleteById(id);
    }
    
    
}
