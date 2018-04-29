/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lpaalva
 */



@Getter
@Setter
public class Notes {
    private String id;

    private Recipe recipe;
    private String recipeNotes;

	public Notes(String id, Recipe recipe, String recipeNotes) {
		this.id = id;
		this.recipe = recipe;
		this.recipeNotes = recipeNotes;
	}

	public Notes() {
	}
   
}
