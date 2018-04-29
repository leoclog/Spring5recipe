/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.services;

import com.zelidre.spring5recipeapp.commands.RecipeCommand;
import com.zelidre.spring5recipeapp.domain.Recipe;
import java.util.Set;

/**
 *
 * @author lpaalva
 */
public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(String id);
    void deleteById(String id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findCommandById(String id);
}
