package com.zelidre.spring5recipeapp.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.zelidre.spring5recipeapp.domain.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}
