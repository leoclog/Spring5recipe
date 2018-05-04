package com.zelidre.spring5recipeapp.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.zelidre.spring5recipeapp.domain.Category;

import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String>{

	Mono<Category> findByDescription(String description);
}
