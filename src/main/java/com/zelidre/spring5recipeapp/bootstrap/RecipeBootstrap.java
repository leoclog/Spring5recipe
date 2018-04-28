/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zelidre.spring5recipeapp.domain.Category;
import com.zelidre.spring5recipeapp.domain.Difficulty;
import com.zelidre.spring5recipeapp.domain.Ingredient;
import com.zelidre.spring5recipeapp.domain.Notes;
import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;
import com.zelidre.spring5recipeapp.repositories.CategoryRepository;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;
import com.zelidre.spring5recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author lpaalva
 */
@Slf4j
@Component
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    CategoryRepository categoryRepository;
    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;

    
    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }
    
    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
    	log.debug("Loading Bootstrap Data");
    	
    	recipeRepository.saveAll(getRecipes());
        
    }
    
    private List<Recipe> getRecipes(){
    	
    	List<Recipe> recipes = new ArrayList<>(2);
    	
    	
        
        Recipe recipe = new Recipe();
        recipe.setDescription("Perfect Guacamole");
        recipe.setCookTime(0);
        recipe.setPrepTime(10);
        recipe.setSource("simply recipes");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe.setDirections("1 Cut avocado, remove flesh.\n2 Mash with a fork\n"
                           + "3 Add salt, lime juice, and the rest\n"
                           + "4 Cover with plastic and chill to store");
        recipe.setServings(4);
        recipe.setDifficulty(Difficulty.EASY);
        
        Notes notes = new Notes();
        
        notes.setRecipeNotes("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. ");
        recipe.setNotes(notes);
        
        recipe.getCategories().add(getCategory("American"));
        recipe.getCategories().add(getCategory("Mexican"));
        
        
        recipe.addIngredient(new Ingredient("ripe avocados",
                                       BigDecimal.valueOf(2),
                                       getUom("")));
        
        recipe.addIngredient(new Ingredient("Kosher Salt",
                                       BigDecimal.valueOf(0.5),
                                       getUom("Teaspoon")));
        
        recipe.addIngredient(new Ingredient("Fresh Lime Juice",
                                       BigDecimal.valueOf(1),
                                       getUom("Tablespoon")));
        
        
        
        recipes.add(recipe);
  
        log.debug("Recipe added");
        
        Recipe recipe2 = new Recipe();
        
        recipe2.setDescription("Spicy Grilled Chicken Tacos");
        recipe2.setCookTime(0);
        recipe2.setPrepTime(10);
        recipe2.setSource("simply recipes");
        recipe2.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        
        Notes notes2 = new Notes();
        
        notes2.setRecipeNotes("6 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. ");
        recipe2.setNotes(notes2);
        recipe2.setDifficulty(Difficulty.MODERATE);
                
              
       
        recipe2.addIngredient(new Ingredient("ancho chili pwoder",
                                       BigDecimal.valueOf(2),
                                       getUom("Tablespoon")));
        
        recipe2.addIngredient(new Ingredient("dried oregano",
                                       BigDecimal.valueOf(1),
                                       getUom("Teaspoon")));
        
        recipe2.addIngredient(new Ingredient("sugar",
                                       BigDecimal.valueOf(1),
                                       getUom("Teaspoon")));
        
        
        recipe2.getCategories().add(getCategory("American"));
        recipe2.getCategories().add(getCategory("Mexican"));
        
    
        
        
        recipes.add(recipe2);
        
        return recipes;
        
    }
    
    private UnitOfMeasure getUom(String uomstr){
       Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription(uomstr);
       if (!uom.isPresent()){
           throw new RuntimeException("Expected UOM not found: " + uomstr);
       }   
       return uom.get();
    }
    
       private Category getCategory(String catstr){
           
       Optional<Category> cat = categoryRepository.findByDescription(catstr);
       if (!cat.isPresent()){
           throw new RuntimeException("Expected Category not found: " + catstr);
       }   
       return cat.get();
    }
}
