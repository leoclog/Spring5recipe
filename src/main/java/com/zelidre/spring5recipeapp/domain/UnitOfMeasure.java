/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.domain;

import org.springframework.data.annotation.Id;
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
public class UnitOfMeasure {
    @Id
    private String id;
    private String description;
    
    
}
