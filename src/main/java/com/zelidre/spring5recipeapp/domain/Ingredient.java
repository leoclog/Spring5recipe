/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.domain;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lpaalva
 */
@Getter
@Setter
public class Ingredient {
	
    private String id=UUID.randomUUID().toString();
    
    private String description;
    private BigDecimal amount;
    
    @DBRef
    private UnitOfMeasure uom;

    
    public Ingredient() {
    }

    
    public Ingredient(String description, BigDecimal amount, Recipe recipe, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
 //       this.recipe = recipe;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }
    
    
 

     
}
