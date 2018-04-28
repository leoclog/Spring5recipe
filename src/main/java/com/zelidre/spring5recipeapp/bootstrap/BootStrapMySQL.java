package com.zelidre.spring5recipeapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.domain.Category;
import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;
import com.zelidre.spring5recipeapp.repositories.CategoryRepository;
import com.zelidre.spring5recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootStrapMySQL implements ApplicationListener<ContextRefreshedEvent>{
	
	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository uomRepository;
	
	public BootStrapMySQL(CategoryRepository categoryRepository, UnitOfMeasureRepository uomRepository) {
		this.categoryRepository = categoryRepository;
		this.uomRepository = uomRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if (categoryRepository.count() == 0L) {
			log.debug("Loading Categories");
			loadCategories();
		}
		
		if (uomRepository.count() == 0L) {
			log.debug("Loading UnitOfMeasures");
			loadUnitOfMeasures();
		}
		
	}

	private void loadUnitOfMeasures() {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setDescription("Teaspoon");
		uomRepository.save(uom1);
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setDescription("Tablespoon");
		uomRepository.save(uom2);
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setDescription("Cup");
		uomRepository.save(uom3);
		UnitOfMeasure uom4 = new UnitOfMeasure();
		uom4.setDescription("Pinch");
		uomRepository.save(uom4);
		UnitOfMeasure uom5 = new UnitOfMeasure();
		uom5.setDescription("Ounce");
		uomRepository.save(uom5);
		UnitOfMeasure uom6 = new UnitOfMeasure();
		uom6.setDescription("Dash");
		uomRepository.save(uom6);
		UnitOfMeasure uom7 = new UnitOfMeasure();
		uom7.setDescription("");
		uomRepository.save(uom7);
		
		
	}

	private void loadCategories() {
		Category cat1 = new Category();
		cat1.setDescription("American");
		categoryRepository.save(cat1);
		Category cat2 = new Category();
		cat2.setDescription("Italian");
		categoryRepository.save(cat2);
		Category cat3 = new Category();
		cat3.setDescription("Mexican");
		categoryRepository.save(cat3);
		Category cat4 = new Category();
		cat4.setDescription("Fast Food");
		categoryRepository.save(cat4);

		
	}
	
	
	

}
