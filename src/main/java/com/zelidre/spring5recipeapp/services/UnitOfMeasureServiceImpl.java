package com.zelidre.spring5recipeapp.services;


import org.springframework.stereotype.Service;

import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl  implements UnitOfMeasureService{

	private final UnitOfMeasureToUnitOfMeasureCommand uomTuomC;
	private final UnitOfMeasureReactiveRepository uomReactiveRepository;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureToUnitOfMeasureCommand uomTuomC,
			UnitOfMeasureReactiveRepository uomReactiveRepository) {
		this.uomTuomC = uomTuomC;
		this.uomReactiveRepository = uomReactiveRepository;
	}

	@Override
	public Flux<UnitOfMeasureCommand> listAllUoms() {
		return uomReactiveRepository
			   .findAll()
			   .map(uomTuomC::convert);
//		return StreamSupport.stream(uomRepository.findAll()
//			            .spliterator(), false)
//							.map(uomTuomC::convert)
//							.collect(Collectors.toSet());
	}

}
