package com.zelidre.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.commands.CategoryCommand;
import com.zelidre.spring5recipeapp.domain.Category;

import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand>{
	
	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category source) {
		if (source == null) {
			return null;
		}
		
		final CategoryCommand catCommand = new CategoryCommand();
		catCommand.setId(source.getId());
		catCommand.setDescription(source.getDescription());
		return catCommand;
	}

}
