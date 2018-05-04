package com.zelidre.spring5recipeapp.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zelidre.spring5recipeapp.domain.Category;
import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

	private static final String FIND_ME = "FindMe";
	@Autowired
	UnitOfMeasureReactiveRepository uomReactiveRepository;
	
	@Before
	public void setUp() throws Exception {
		uomReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void testRecipeSave() throws Exception{
		UnitOfMeasure uom = new UnitOfMeasure();
		
		uom.setDescription(FIND_ME);
		
		uomReactiveRepository.save(uom).block();
		
		Long count = uomReactiveRepository.count().block();
		
		assertEquals(Long.valueOf(1L), count);
	}
	
	@Test
	public void findByDescriptionTest() throws Exception {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(FIND_ME);
		uomReactiveRepository.save(uom).then().block();
		 
		UnitOfMeasure fetchUom = uomReactiveRepository.findByDescription(FIND_ME).block();
		
		assertNotNull(fetchUom.getId());
	}
}
