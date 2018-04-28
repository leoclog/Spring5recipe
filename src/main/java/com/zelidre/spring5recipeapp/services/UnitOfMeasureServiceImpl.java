package com.zelidre.spring5recipeapp.services;


import java.util.Set;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl  implements UnitOfMeasureService{

	private final UnitOfMeasureToUnitOfMeasureCommand uomTuomC;
	private final UnitOfMeasureRepository uomRepository;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureToUnitOfMeasureCommand uomTuomC,
			UnitOfMeasureRepository uomRepository) {
		this.uomTuomC = uomTuomC;
		this.uomRepository = uomRepository;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		return StreamSupport.stream(uomRepository.findAll()
				            .spliterator(), false)
							.map(uomTuomC::convert)
							.collect(Collectors.toSet());
	}

}
