package com.tguzik.util.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.mockito.internal.matchers.LessThan;

/**
 * Most test cases are already covered in {@link ValueTest}
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class StringValueTest
{
    private StringValue a;
    private StringValue b;

    @Before
    public void setUp( ) throws Exception {

        a = new StringValueHelper( "a" );
        b = new StringValueHelper( "b" );
    }

    @Test
    public void testCompareTo_a_before_b( ) {
        assertThat( a.compareTo( b ), new LessThan<>( 0 ) );
    }

    @Test
    public void testCompareTo_valueToItself( ) {
        assertEquals( 0, a.compareTo( a ) );
        assertEquals( 0, b.compareTo( b ) );
    }

    @Test
    public void testCompareTo_b_after_a( ) {
        assertThat( b.compareTo( a ), new GreaterThan<>( 0 ) );
    }
}

class StringValueHelper extends StringValue
{
    public StringValueHelper( String str ) {
        super( str );
    }
}
