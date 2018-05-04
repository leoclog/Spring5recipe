package com.zelidre.spring5recipeapp.converters;

import org.junit.Before;
import org.junit.Test;

import com.zelidre.spring5recipeapp.commands.NotesCommand;
import com.zelidre.spring5recipeapp.domain.Notes;

import static org.junit.Assert.*;

/**
 * Created by jt on 6/21/17.
 */
public class NotesToNotesCommandTest {

    public static final String ID_VALUE = "1";
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void convert() throws Exception {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand notesCommand = converter.convert(notes);

        //then
       
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
        assertEquals(ID_VALUE , notesCommand.getId());
    }

    @Test
    public void testNull() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
    	Notes note = new Notes();
    	note.setId(ID_VALUE);
        assertNotNull(converter.convert(note));
        //assertNotNull(converter.convert(new Notes()));
    }
}