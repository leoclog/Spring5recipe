package com.zelidre.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;

import lombok.Synchronized;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand>{

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (source == null) {
			return null;
		}
		
		final UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId(source.getId());
		uomCommand.setDescription(source.getDescription());
		return uomCommand;
	}

}
