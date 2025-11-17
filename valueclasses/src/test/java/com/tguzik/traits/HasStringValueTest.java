package com.tguzik.traits;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class HasStringValueTest {

  static Stream<String> emptyStrings() {
    return Stream.of( null, "" );
  }

  static Stream<String> blankNonEmptyStrings() {
    return Stream.of( " ", "  ", "     ", "\n", "\t", "\r", "\r\n", "\n\n \r \t \n" );
  }

  static Stream<String> nonBlankStrings() {
    return Stream.of( "!", "*", "0", "1", "x", "01", "10", "12", "xy", "123", "abc", "   x ", "   ,   " );
  }

  record FakeValue(@Nullable String value) implements HasStringValue {
    @Override
    @Nullable
    public String get() {
      return value;
    }
  }

  @ParameterizedTest
  @MethodSource( "emptyStrings" )
  void length_returns_zero_when_contained_value_is_null_or_empty( @Nullable final String value ) {
    final var instance = new FakeValue( value );
    final int actual = instance.length();

    assertThat( actual ).isZero();
  }

  @ParameterizedTest
  @MethodSource( { "blankNonEmptyStrings", "nonBlankStrings" } )
  void length_returns_the_length_of_the_contained_string( final String value ) {
    final var instance = new FakeValue( value );
    final int actual = instance.length();

    assertThat( actual ).isEqualTo( value.length() );
  }

  @ParameterizedTest
  @MethodSource( "emptyStrings" )
  void isEmpty_returns_true_when_contained_value_is_null_or_empty( @Nullable final String value ) {
    final var instance = new FakeValue( value );
    final boolean actual = instance.isEmpty();

    assertThat( actual ).isTrue();
  }

  @ParameterizedTest
  @MethodSource( { "blankNonEmptyStrings", "nonBlankStrings" } )
  void isEmpty_returns_false_when_contained_value_has_non_zero_length( final String value ) {
    final var instance = new FakeValue( value );
    final boolean actual = instance.isEmpty();

    assertThat( actual ).isFalse();
  }

  @ParameterizedTest
  @MethodSource( { "emptyStrings", "blankNonEmptyStrings" } )
  void isBlank_returns_true_when_contained_value_is_null_or_consists_only_of_whitespace( @Nullable final String value ) {
    final var instance = new FakeValue( value );
    final boolean actual = instance.isBlank();

    assertThat( actual ).isTrue();
  }

  @ParameterizedTest
  @MethodSource( "nonBlankStrings" )
  void isBlank_returns_false_when_contained_value_has_non_whitespace_characters( final String value ) {
    final var instance = new FakeValue( value );
    final boolean actual = instance.isBlank();

    assertThat( actual ).isFalse();
  }
}
