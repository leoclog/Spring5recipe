package com.zelidre.spring5recipeapp.converters;

import org.junit.Before;
import org.junit.Test;

import com.zelidre.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.zelidre.spring5recipeapp.domain.UnitOfMeasure;

import static org.junit.Assert.*;

/**
 * Created by jt on 6/21/17.
 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final String ID_VALUE = "1";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID_VALUE);
        uom.setDescription(DESCRIPTION);
        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(ID_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }

}