package com.zelidre.spring5recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;
import com.zelidre.spring5recipeapp.repositories.UnitOfMeasureRepository;
import com.zelidre.spring5recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

import reactor.core.publisher.Flux;

public class UnitOfMeasureServiceImplTest {
	UnitOfMeasureToUnitOfMeasureCommand uomTuomC = new UnitOfMeasureToUnitOfMeasureCommand();
	UnitOfMeasureService uomService;
	
	@Mock
	UnitOfMeasureReactiveRepository uomReactiveRepository;
	
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		uomService = new UnitOfMeasureServiceImpl(uomTuomC,
				uomReactiveRepository);
	}

	@Test
	public void listAllUomstest() {
		//given
	
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId("1");
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("2");
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom2.setId("3");

		
		when(uomReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2, uom3));
		
		List <UnitOfMeasureCommand> uomCs = uomService.listAllUoms().collectList().block();
		assertEquals(3, uomCs.size());
		verify(uomReactiveRepository, times(1)).findAll();
	}

}
