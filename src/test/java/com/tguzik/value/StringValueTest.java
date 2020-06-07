package com.tguzik.value;

import static java.lang.Integer.signum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Most test cases are already covered in {@link ValueTest}
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
    public void setUp() {
        this.newlinesAndSpaces = new StringValueHelper( "\n\n \r \n" );
        this.spacesAndAlpha = new StringValueHelper( "  a " );
        this.nullString = new StringValueHelper( null );
        this.emptyString = new StringValueHelper( "" );
        this.spaces = new StringValueHelper( "   " );
        this.abcd = new StringValueHelper( "abcd" );
        this.a = new StringValueHelper( "a" );
        this.b = new StringValueHelper( "b" );
    }

    @Test
    public void testCompareTo_a_before_b() {
        assertThat( a.compareTo( b ) ).isNotZero();
        assertThat( a ).isNotEqualTo( b );
    }

    @Test
    public void compareTo_returns_zero_when_comparing_value_to_itself() {
        //noinspection EqualsWithItself
        @SuppressWarnings( "SelfComparison" )
        final int atoa = a.compareTo( a );
        assertThat( atoa ).isZero();
        assertThat( a ).isEqualTo( a ); // ensure that equals is not b0rken

        //noinspection EqualsWithItself
        @SuppressWarnings( "SelfComparison" )
        final int btob = b.compareTo( b );
        assertThat( btob ).isZero();
        assertThat( b ).isEqualTo( b ); // ensure that equals is not b0rken
    }

    @Test
    public void compareTo_b_after_a() {
        assertThat( b.compareTo( a ) ).isNotZero();
        assertThat( b ).isNotEqualTo( a );
    }

    /**
     * Contract per {@link java.lang.Comparable}:
     * <tt>sgn(x.compareTo(y)) == -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>
     */
    @Test
    public void compareTo_returns_opposite_values_when_comparing_a_to_b_and_b_to_a() {
        assertThat( signum( a.compareTo( b ) ) ).isEqualTo( -signum( b.compareTo( a ) ) );
    }

    /**
     * Contract per {@link java.lang.Comparable}:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies <tt>x.compareTo(z)&gt;0</tt>
     */
    @Test
    public void compareTo_is_transitive() {
        final StringValue c = new StringValueHelper( "c" );

        assertThat( signum( a.compareTo( b ) ) ).isEqualTo( signum( b.compareTo( c ) ) )
                                                .isEqualTo( signum( a.compareTo( c ) ) );
    }

    /**
     * Contract per {@link java.lang.Comparable}:
     * <tt>x.compareTo(y)==0</tt> implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all <tt>z</tt>
     */
    @Test
    public void compareTo_if_a_and_b_are_equal_and_c_is_geater_than_a_then_c_is_also_greater_than_b() {
        final StringValue a2 = new StringValueHelper( "a" );

        assertThat( a ).isNotSameAs( a2 ).isEqualTo( a2 );

        assertThat( a.compareTo( a2 ) ).isZero();
        assertThat( signum( a.compareTo( b ) ) ).isEqualTo( signum( a2.compareTo( b ) ) );
    }

    /**
     * Contract per {@link java.lang.Comparable}: <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>
     */
    @Test
    public void compareTo_if_a_and_b_are_equal_then_they_should_compare_as_same() {
        final StringValue a2 = new StringValueHelper( "a" );

        assertThat( a ).isNotSameAs( a2 ).isEqualTo( a2 );
        assertThat( a.compareTo( a2 ) ).isZero();

        assertThat( a2 ).isNotSameAs( a ).isEqualTo( a );
        assertThat( a2.compareTo( a ) ).isZero();
    }

    /**
     * Contract per {@link java.lang.Comparable} throws: throws NullPointerException if the specified object is null
     */
    @Test
    public void compareTo_throws_NullPointerException_when_parameter_was_null() {
        try {
            a.compareTo( null );
            fail( "Expected exception" );
        }
        catch ( NullPointerException e ) {
            assertThat( e ).hasMessage( "Parameter cannot be null." ).hasNoCause();
        }
    }

    /**
     * Contract per {@link java.lang.Comparable}:
     * throws ClassCastException if the specified object's type prevents it from being compared to this object.
     */
    @SuppressWarnings( "unchecked" )
    @Test( expected = ClassCastException.class )
    public void compareTo_throws_ClassCastException_when_parameter_was_of_incompatible_type() {
        final Value<Integer> integerValue = new Value<Integer>( Integer.valueOf( 123 ) ) {
        };

        a.compareTo( (Value) integerValue );
    }

    /**
     * Exploding with NullPointerException when one of the value classes has null inside is damn inconvenient.
     * Instead we return zero
     */
    @Test
    public void compareTo_returns_zero_when_at_least_one_value_class_has_null_inside() {
        assertThat( a.compareTo( nullString ) ).isZero();
        assertThat( nullString.compareTo( a ) ).isZero();

        assertThat( emptyString.compareTo( nullString ) ).isZero();
        assertThat( nullString.compareTo( emptyString ) ).isZero();
    }

    @Test
    public void length_returns_string_length_or_zero_if_null() {
        assertThat( a.length() ).isEqualTo( 1 );
        assertThat( abcd.length() ).isEqualTo( 4 );
        assertThat( spaces.length() ).isEqualTo( 3 );
        assertThat( newlinesAndSpaces.length() ).isEqualTo( 6 );
        assertThat( spacesAndAlpha.length() ).isEqualTo( 4 );

        assertThat( emptyString.length() ).isEqualTo( 0 );
        assertThat( nullString.length() ).isEqualTo( 0 );
    }

    @Test
    public void isEmpty_returns_true_on_length_equal_to_zero() {
        assertThat( a.isEmpty() ).isFalse();
        assertThat( abcd.isEmpty() ).isFalse();
        assertThat( spaces.isEmpty() ).isFalse();
        assertThat( spacesAndAlpha.isEmpty() ).isFalse();
        assertThat( newlinesAndSpaces.isEmpty() ).isFalse();

        assertThat( emptyString.isEmpty() ).isTrue();
        assertThat( nullString.isEmpty() ).isTrue();
    }

    @Test
    public void isBlank_returns_true_on_whitespace_empty_and_null_string() {
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
