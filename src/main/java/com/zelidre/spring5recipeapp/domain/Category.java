/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.domain;

import java.util.Set;

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
@Document
public class Category {
	@Id
    private String id;
    private String description;
    @DBRef
    private Set<Recipe> recipes;

}
