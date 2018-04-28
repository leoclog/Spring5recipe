package com.zelidre.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zelidre.spring5recipeapp.commands.NotesCommand;
import com.zelidre.spring5recipeapp.domain.Notes;

import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		if (source == null) {
			return null;
		}
		
		final NotesCommand noteCommand = new NotesCommand();
		noteCommand.setId(source.getId());
		noteCommand.setRecipeNotes(source.getRecipeNotes());
		return noteCommand;
	}

}
