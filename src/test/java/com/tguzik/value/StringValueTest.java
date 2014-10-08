package com.tguzik.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Most test cases are already covered in {@link ValueTest}
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class StringValueTest {
    private StringValue newlinesAndSpaces;
    private StringValue spacesAndAlpha;
    private StringValue emptyString;
    private StringValue nullString;
    private StringValue spaces;
    private StringValue abcd;
    private StringValue a;
    private StringValue b;

    @Before
    public void setUp() throws Exception {
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
    public void testCompareTo_a_before_b() {
        assertThat( a.compareTo( b ) ).isLessThan( 0 );
    }

    @Test
    public void testCompareTo_valueToItself() {
        assertThat( a.compareTo( a ) ).isZero();
        assertThat( b.compareTo( b ) ).isZero();
    }

    @Test
    public void testCompareTo_b_after_a() {
        assertThat( b.compareTo( a ) ).isGreaterThan( 0 );
    }

    @Test
    public void testLength() {
        assertThat( a.length() ).isEqualTo( 1 );
        assertThat( abcd.length() ).isEqualTo( 4 );
        assertThat( spaces.length() ).isEqualTo( 3 );
        assertThat( newlinesAndSpaces.length() ).isEqualTo( 6 );
        assertThat( spacesAndAlpha.length() ).isEqualTo( 4 );

        assertThat( emptyString.length() ).isEqualTo( 0 );
        assertThat( nullString.length() ).isEqualTo( 0 );
    }

    @Test
    public void testIsEmpty() {
        assertThat( a.isEmpty() ).isFalse();
        assertThat( abcd.isEmpty() ).isFalse();
        assertThat( spaces.isEmpty() ).isFalse();
        assertThat( spacesAndAlpha.isEmpty() ).isFalse();
        assertThat( newlinesAndSpaces.isEmpty() ).isFalse();

        assertThat( emptyString.isEmpty() ).isTrue();
        assertThat( nullString.isEmpty() ).isTrue();
    }

    @Test
    public void testIsBlank() {
        assertThat( a.isBlank() ).isFalse();
        assertThat( abcd.isBlank() ).isFalse();
        assertThat( spacesAndAlpha.isBlank() ).isFalse();

        assertThat( newlinesAndSpaces.isBlank() ).isTrue();
        assertThat( spaces.isBlank() ).isTrue();
        assertThat( emptyString.isBlank() ).isTrue();
        assertThat( nullString.isBlank() ).isTrue();
    }

    static class StringValueHelper extends StringValue {
        public StringValueHelper( String str ) {
            super( str );
        }
    }
}
