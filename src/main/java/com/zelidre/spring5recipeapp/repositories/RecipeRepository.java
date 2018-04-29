/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.repositories;

import com.zelidre.spring5recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author lpaalva
 */
public interface RecipeRepository extends CrudRepository<Recipe, String>{
    
}
