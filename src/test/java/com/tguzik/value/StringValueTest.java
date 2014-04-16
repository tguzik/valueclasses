package com.tguzik.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.tguzik.value.StringValue;

/**
 * Most test cases are already covered in {@link ValueTest}
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class StringValueTest
{
    private StringValue newlinesAndSpaces;
    private StringValue spacesAndAlpha;
    private StringValue emptyString;
    private StringValue nullString;
    private StringValue spaces;
    private StringValue abcd;
    private StringValue a;
    private StringValue b;

    @Before
    public void setUp( ) throws Exception {
        newlinesAndSpaces = new StringValueHelper( "\n\n \r \n" );
        spacesAndAlpha = new StringValueHelper( "  a " );
        nullString = new StringValueHelper( null );
        emptyString = new StringValueHelper( "" );
        spaces = new StringValueHelper( "   " );
        abcd = new StringValueHelper( "abcd" );
        a = new StringValueHelper( "a" );
        b = new StringValueHelper( "b" );
    }

    @Test
    public void testCompareTo_a_before_b( ) {
        assertTrue( a.compareTo( b ) < 0 );
    }

    @Test
    public void testCompareTo_valueToItself( ) {
        assertEquals( 0, a.compareTo( a ) );
        assertEquals( 0, b.compareTo( b ) );
    }

    @Test
    public void testCompareTo_b_after_a( ) {
        assertTrue( b.compareTo( a ) > 0 );
    }

    @Test
    public void testLength( ) {
        assertEquals( 1, a.length() );
        assertEquals( 4, abcd.length() );
        assertEquals( 3, spaces.length() );
        assertEquals( 6, newlinesAndSpaces.length() );
        assertEquals( 4, spacesAndAlpha.length() );

        assertEquals( 0, emptyString.length() );
        assertEquals( 0, nullString.length() );
    }

    @Test
    public void testIsEmpty( ) {
        assertFalse( a.isEmpty() );
        assertFalse( abcd.isEmpty() );
        assertFalse( spaces.isEmpty() );
        assertFalse( spacesAndAlpha.isEmpty() );
        assertFalse( newlinesAndSpaces.isEmpty() );

        assertTrue( emptyString.isEmpty() );
        assertTrue( nullString.isEmpty() );
    }

    @Test
    public void testIsBlank( ) {
        assertFalse( a.isBlank() );
        assertFalse( abcd.isBlank() );
        assertFalse( spacesAndAlpha.isBlank() );

        assertTrue( newlinesAndSpaces.isBlank() );
        assertTrue( spaces.isBlank() );
        assertTrue( emptyString.isBlank() );
        assertTrue( nullString.isBlank() );
    }

    static class StringValueHelper extends StringValue
    {
        public StringValueHelper( String str ) {
            super( str );
        }
    }
}
