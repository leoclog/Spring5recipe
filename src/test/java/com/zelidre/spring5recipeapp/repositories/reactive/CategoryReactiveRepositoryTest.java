package com.zelidre.spring5recipeapp.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zelidre.spring5recipeapp.domain.Category;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;
	
	@Before
	public void setUp() throws Exception {
		categoryReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void testRecipeSave() throws Exception{
		Category category = new Category();
		
		category.setDescription("Test description");
		
		categoryReactiveRepository.save(category).block();
		
		Long count = categoryReactiveRepository.count().block();
		
		assertEquals(Long.valueOf(1L), count);
	}
	
	@Test
	public void findByDescriptionTest() throws Exception {
		Category category = new Category();
		category.setDescription("FindMe");
		categoryReactiveRepository.save(category).then().block();
		 
		Category fetchCat = categoryReactiveRepository.findByDescription("FindMe").block();
		
		assertNotNull(fetchCat.getId());
	}
}
