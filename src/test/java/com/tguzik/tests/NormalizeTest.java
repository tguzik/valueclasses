package com.tguzik.tests;

import static com.tguzik.tests.Normalize.newLines;
import static com.tguzik.tests.Normalize.tabsToSpaces;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NormalizeTest {

  @Test
  public void newLines_kills_off_CR_and_leaves_LF() {
    assertThat( newLines( "abc   \r\n\r  " ) ).isEqualTo( "abc   \n  " );
    assertThat( newLines( "  \n \nabc  \r" ) ).isEqualTo( "  \n \nabc  \n" );
    assertThat( newLines( "\n\n\n" ) ).isEqualTo( "\n\n\n" );
    assertThat( newLines( "\r\n\r\n\r" ) ).isEqualTo( "\n\n" );
  }

  @Test
  public void newLines_returns_empty_string_given_null() {
    assertThat( newLines( null ) ).isNotNull().isEmpty();
  }

  @Test
  public void newLines_returns_empty_string_given_empy_string() {
    assertThat( newLines( "" ) ).isEqualTo( "" );
  }

  @Test
  public void newLines_returns_the_same_string_if_it_didnt_have_any_newlines() {
    assertThat( newLines( "abc" ) ).isEqualTo( "abc" );
  }

  @Test
  public void tabsToSpaces_changes_tabulation_character_to_given_number_of_spaces() {
    assertThat( tabsToSpaces( "a", 4 ) ).isEqualTo( "a" );

    assertThat( tabsToSpaces( "\ta\t", 4 ) ).isEqualTo( "    a    " );
    assertThat( tabsToSpaces( "a \t", 4 ) ).isEqualTo( "a     " );
    assertThat( tabsToSpaces( "\ta\r\nx\t\r\n", 4 ) ).isEqualTo( "    a\r\nx    \r\n" );
  }

  @Test
  public void tabsToSpaces_returns_empty_string_given_null() {
    assertThat( tabsToSpaces( null, 4 ) ).isNotNull().isEmpty();
  }

  @Test
  public void tabsToSpaces_returns_empty_string_given_empty_string() {
    assertThat( tabsToSpaces( "", 4 ) ).isEqualTo( "" );
  }

  @Test
  public void tabsToSpaces_works_consistently_with_different_tab_widths() {
    assertThat( tabsToSpaces( "\t", Integer.MIN_VALUE ) ).isEqualTo( "" );
    assertThat( tabsToSpaces( "\t", -1024 ) ).isEqualTo( "" );
    assertThat( tabsToSpaces( "\t", -1 ) ).isEqualTo( "" );
    assertThat( tabsToSpaces( "\t", 0 ) ).isEqualTo( "" );
    assertThat( tabsToSpaces( "\t", 1 ) ).isEqualTo( " " );
    assertThat( tabsToSpaces( "\t", 2 ) ).isEqualTo( "  " );
    assertThat( tabsToSpaces( "\t", 4 ) ).isEqualTo( "    " );
  }

  @Test
  public void tabsToSpaces_negative_tab_width_means_remove_tabulation_characters() {
    assertThat( tabsToSpaces( " \t", Integer.MIN_VALUE ) ).isEqualTo( " " );
    assertThat( tabsToSpaces( " \t", -1024 ) ).isEqualTo( " " );
    assertThat( tabsToSpaces( " \t", -1 ) ).isEqualTo( " " );
    assertThat( tabsToSpaces( " \t", 0 ) ).isEqualTo( " " );
  }
}
