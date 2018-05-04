package com.zelidre.spring5recipeapp.repositories;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryTest {
	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByDescription() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription("Teaspoon");
		
		unitOfMeasureRepository.save(uom);
		Optional<UnitOfMeasure> uomOptional = 
				unitOfMeasureRepository.findByDescription("Teaspoon");
		assertEquals("Teaspoon", uomOptional.get().getDescription());
	}


}
