package com.tguzik.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class NormalizeTest {

  static Stream<Arguments> newLinesTestCases() {
    return Stream.of( Arguments.of( null, "" ),
                      Arguments.of( "", "" ),
                      Arguments.of( "abc", "abc" ),
                      Arguments.of( "abc   \r\n\r  ", "abc   \n  " ),
                      Arguments.of( "  \n \nabc  \r", "  \n \nabc  \n" ),
                      Arguments.of( "\n\n\n", "\n\n\n" ),
                      Arguments.of( "\r\n\r\n\r", "\n\n" ) );
  }

  @ParameterizedTest
  @MethodSource( "newLinesTestCases" )
  void newLines_returns_expected_string_for_given_argument( @Nullable final String input, final String expected ) {
    final String actual = Normalize.newLines( input );

    assertThat( actual ).isEqualTo( expected );
  }

  static Stream<Arguments> tabsToSpacesTestCases() {
    return Stream.of( Arguments.of( null, "" ),
                      Arguments.of( "", "" ),
                      Arguments.of( "a", "a" ),
                      Arguments.of( "\ta\t", "    a    " ),
                      Arguments.of( "a \t", "a     " ),
                      Arguments.of( "\ta\r\nx\t\r\n", "    a\r\nx    \r\n" ) );
  }

  @ParameterizedTest
  @MethodSource( "tabsToSpacesTestCases" )
  void tabsToSpaces_returns_expected_string_for_given_argument( @Nullable final String input, final String expected ) {
    final String actual = Normalize.tabsToSpaces( input, 4 );

    assertThat( actual ).isEqualTo( expected );
  }

  @ParameterizedTest
  @ValueSource( ints = { Integer.MIN_VALUE, -1024, -42, -3, -2, -1, 0 } )
  void tabsToSpaces_called_with_nonpositive_tab_width_removes_tabulation_characters( final int tabWidth ) {
    final String actual = Normalize.tabsToSpaces( "\t", tabWidth );

    assertThat( actual ).isEmpty();
  }

  @ParameterizedTest
  @ValueSource( ints = { 1, 2, 3, 4, 8, 16, 32, 42 } )
  void tabsToSpaces_called_with_positive_tab_width_will_convert_the_tab_to_given_number_of_spaces( final int tabWidth ) {
    final String actual = Normalize.tabsToSpaces( "\t", tabWidth );

    assertThat( actual ).hasSize( tabWidth ).isEqualTo( " ".repeat( tabWidth ) );
  }
}
