/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.controllers;

import com.zelidre.spring5recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lpaalva
 */
@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

  
    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
//        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
//        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Ounce");
//        System.out.println("Cat id is: " + categoryOptional.get().getId());
//        System.out.println("UOM id is: " + unitOfMeasureOptional.get().getId());
        model.addAttribute("recipes", recipeService.getRecipes());
 //       System.out.println("Length : " + recipeService.getRecipes().size());
        return "index";
        
    }
    
}
