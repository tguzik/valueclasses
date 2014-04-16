package com.tguzik.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tguzik.unittests.SettableHashCode;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * 
 */
public class SettableHashCodeTest
{
    private SettableHashCode object;

    @Before
    public void setUp( ) {
        object = SettableHashCode.create( 42 );
    }

    @Test
    public void testSettableHashCode( ) {
        assertEquals( 42, object.hashCode() );
        assertEquals( object.getValue().intValue(), object.hashCode() );
    }
}
