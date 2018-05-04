package com.zelidre.spring5recipeapp.services;

import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
		Flux<UnitOfMeasureCommand> listAllUoms();
}
