package com.tguzik.value;

import static java.lang.Integer.signum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Most test cases are already covered in {@link ValueTest}
 */
class StringValueTest {

  @Test
  void testCompareTo_a_before_b() {
    final var a = new FakeStringValue( "a" );
    final var b = new FakeStringValue( "b" );

    assertThat( a ).isNotEqualTo( b );
    assertThat( a.compareTo( b ) ).isNegative();
  }

  @Test
  void compareTo_returns_zero_when_comparing_equal_values() {
    final var a = new FakeStringValue( "a" );
    final var equalValue = new FakeStringValue( "a" );

    assertThat( a ).isEqualTo( equalValue );
    assertThat( a.compareTo( equalValue ) ).isZero();
  }

  @Test
  void compareTo_b_after_a() {
    final var a = new FakeStringValue( "a" );
    final var b = new FakeStringValue( "b" );

    assertThat( b ).isNotEqualTo( a );
    assertThat( b.compareTo( a ) ).isPositive();
  }

  /**
   * Contract per {@link java.lang.Comparable}:
   * <tt>sgn(x.compareTo(y)) == -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>
   */
  @Test
  void compareTo_returns_opposite_values_when_comparing_a_to_b_and_b_to_a() {
    final var a = new FakeStringValue( "a" );
    final var b = new FakeStringValue( "b" );

    final int atob = Integer.signum( a.compareTo( b ) );
    final int btoa = -Integer.signum( b.compareTo( a ) );

    assertThat( atob ).isEqualTo( btoa );
  }

  /**
   * Contract per {@link java.lang.Comparable}:
   * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies <tt>x.compareTo(z)&gt;0</tt>
   */
  @Test
  void compareTo_is_transitive() {
    final var a = new FakeStringValue( "a" );
    final var b = new FakeStringValue( "b" );
    final var c = new FakeStringValue( "c" );

    final int atob = Integer.signum( a.compareTo( b ) );
    final int btoc = Integer.signum( b.compareTo( c ) );
    final int atoc = Integer.signum( a.compareTo( c ) );

    assertThat( atob ).isEqualTo( btoc ).isEqualTo( atoc );
  }

  /**
   * Contract per {@link java.lang.Comparable}:
   * <tt>x.compareTo(y)==0</tt> implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all <tt>z</tt>
   */
  @Test
  void compareTo_if_a_and_b_are_equal_and_c_is_geater_than_a_then_c_is_also_greater_than_b() {
    final var a = new FakeStringValue( "a" );
    final var a2 = new FakeStringValue( "a" );
    final var b = new FakeStringValue( "b" );

    assertThat( a ).isNotSameAs( a2 ).isEqualTo( a2 );

    assertThat( a.compareTo( a2 ) ).isZero();
    assertThat( signum( a.compareTo( b ) ) ).isEqualTo( signum( a2.compareTo( b ) ) );
  }

  /**
   * Contract per {@link java.lang.Comparable}: <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>
   */
  @Test
  void compareTo_if_a_and_b_are_equal_then_they_should_compare_as_same() {
    final var a = new FakeStringValue( "a" );
    final var a2 = new FakeStringValue( "a" );

    assertThat( a ).isNotSameAs( a2 ).isEqualTo( a2 );
    assertThat( a.compareTo( a2 ) ).isZero();

    assertThat( a2 ).isNotSameAs( a ).isEqualTo( a );
    assertThat( a2.compareTo( a ) ).isZero();
  }

  /**
   * Contract per {@link java.lang.Comparable} throws: throws NullPointerException if the specified object is null
   */
  @Test
  void compareTo_throws_NullPointerException_when_parameter_was_null() {
    final var a = new FakeStringValue( "a" );

    assertThrows( NullPointerException.class, () -> a.compareTo( null ) );
  }

  /**
   * Exploding with NullPointerException when one of the value classes has null inside is damn inconvenient.
   * Instead we return zero
   */
  @Test
  void compareTo_returns_zero_when_at_least_one_value_class_has_null_inside() {
    final var a = new FakeStringValue( "a" );
    final var emptyString = new FakeStringValue( "" );
    final var nullString = new FakeStringValue( null );

    assertThat( a.compareTo( nullString ) ).isZero();
    assertThat( nullString.compareTo( a ) ).isZero();

    assertThat( emptyString.compareTo( nullString ) ).isZero();
    assertThat( nullString.compareTo( emptyString ) ).isZero();
  }

  static Stream<String> blankNonEmptyStrings() {
    return Stream.of( " ", "  ", "     ", "\n", "\t", "\r", "\r\n", "\n\n \r \t \n" );
  }

  static Stream<String> nonBlankStrings() {
    return Stream.of( "!", "*", "0", "1", "x", "01", "10", "12", "xy", "123", "abc" );
  }

  @ParameterizedTest
  @MethodSource( { "blankNonEmptyStrings", "nonBlankStrings" } )
  void length_returns_the_exact_length_of_the_contained_string( final String value ) {
    final var instance = new FakeStringValue( value );
    final int actual = instance.length();

    assertThat( actual ).isEqualTo( value.length() );
  }

  @ParameterizedTest
  @NullSource
  @ValueSource( strings = { "" } )
  void isEmpty_returns_true_when_contained_value_is_an_empty_string_or_null( @Nullable final String value ) {
    final var instance = new FakeStringValue( value );
    final boolean actual = instance.isEmpty();

    assertThat( actual ).isTrue();
  }

  @ParameterizedTest
  @MethodSource( { "blankNonEmptyStrings", "nonBlankStrings" } )
  void isEmpty_returns_false_when_contained_value_has_length_greater_than_zero( final String value ) {
    final var instance = new FakeStringValue( value );
    final boolean actual = instance.isEmpty();

    assertThat( actual ).isFalse();
  }

  @ParameterizedTest
  @NullSource
  @ValueSource( strings = { "" } )
  @MethodSource( "blankNonEmptyStrings" )
  void isBlank_returns_true_when_contained_value_is_a_blank_string_or_null( @Nullable final String value ) {
    final var instance = new FakeStringValue( value );
    final boolean actual = instance.isBlank();

    assertThat( actual ).isTrue();
  }

  @ParameterizedTest
  @MethodSource( "nonBlankStrings" )
  void isBlank_returns_false_when_contained_value_is_a_nonblank_string( final String value ) {
    final var instance = new FakeStringValue( value );
    final boolean actual = instance.isEmpty();

    assertThat( actual ).isFalse();
  }

  static class FakeStringValue extends StringValue {
    public FakeStringValue( String str ) {
      super( str );
    }
  }
}
