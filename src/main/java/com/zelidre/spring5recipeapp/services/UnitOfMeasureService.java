package com.zelidre.spring5recipeapp.services;

import java.util.Set;

import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
		Set<UnitOfMeasureCommand> listAllUoms();
}
