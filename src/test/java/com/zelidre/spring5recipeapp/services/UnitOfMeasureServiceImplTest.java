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

public class UnitOfMeasureServiceImplTest {
	private final UnitOfMeasureToUnitOfMeasureCommand uomTuomC;
	
	UnitOfMeasureServiceImpl uomService;
	
	@Mock
	UnitOfMeasureRepository uomRepository;
	
	
	
	public UnitOfMeasureServiceImplTest() {
		this.uomTuomC = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		uomService = new UnitOfMeasureServiceImpl(uomTuomC,
				uomRepository);
	}

	@Test
	public void listAllUomstest() {
		//given
		List<UnitOfMeasure> uomList = new ArrayList<>(3);
		
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom2.setId(3L);
		uomList.add(uom1);
		uomList.add(uom2);
		uomList.add(uom3);

		
		when(uomRepository.findAll()).thenReturn(uomList);
		
		Set<UnitOfMeasureCommand> uomCs = uomService.listAllUoms();
		assertEquals(3, uomCs.size());
		verify(uomRepository, times(1)).findAll();
	}

}
