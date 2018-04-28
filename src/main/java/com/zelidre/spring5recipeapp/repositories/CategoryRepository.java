/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelidre.spring5recipeapp.repositories;

import com.zelidre.spring5recipeapp.domain.Category;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author lpaalva
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
