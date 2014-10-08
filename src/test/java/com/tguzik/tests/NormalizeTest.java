package com.tguzik.tests;

import static com.tguzik.tests.Normalize.newLines;
import static com.tguzik.tests.Normalize.tabsToSpaces;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class NormalizeTest {
    @Test
    public void testNewLines() {
        assertEquals( "abc   \n  ", newLines( "abc   \r\n\r  " ) );
        assertEquals( "  \n \nabc  \n", newLines( "  \n \nabc  \r" ) );
        assertEquals( "\n\n\n", newLines( "\n\n\n" ) );
        assertEquals( "\n\n", newLines( "\r\n\r\n\r" ) );
    }

    @Test
    public void testNewLines_nullGiven_nullReturned() {
        assertThat( newLines( null ) ).isNull();
    }

    @Test
    public void testNewLines_emptyString() {
        assertThat( newLines( "" ) ).isEqualTo( "" );
    }

    @Test
    public void testNewLines_stringWithoutWhitespaceNotChanged() {
        assertThat( newLines( "abc" ) ).isEqualTo( "abc" );
    }

    @Test
    public void testTabsToSpaces() {
        assertThat( tabsToSpaces( "a", 4 ) ).isEqualTo( "a" );

        assertThat( tabsToSpaces( "\ta\t", 4 ) ).isEqualTo( "    a    " );
        assertThat( tabsToSpaces( "a \t", 4 ) ).isEqualTo( "a     " );
        assertThat( tabsToSpaces( "\ta\r\nx\t\r\n", 4 ) ).isEqualTo( "    a\r\nx    \r\n" );
    }

    @Test
    public void testTabsToSpaces_nullGiven_nullReturned() {
        assertThat( tabsToSpaces( null, 4 ) ).isNull();
    }

    @Test
    public void testTabsToSpaces_emptyString() {
        assertThat( tabsToSpaces( "", 4 ) ).isEqualTo( "" );
    }

    @Test
    public void testTabsToSpaces_differentTabWidths() {
        assertThat( tabsToSpaces( "\t", Integer.MIN_VALUE ) ).isEqualTo( "" );
        assertThat( tabsToSpaces( "\t", -1024 ) ).isEqualTo( "" );
        assertThat( tabsToSpaces( "\t", -1 ) ).isEqualTo( "" );
        assertThat( tabsToSpaces( "\t", 0 ) ).isEqualTo( "" );
        assertThat( tabsToSpaces( "\t", 1 ) ).isEqualTo( " " );
        assertThat( tabsToSpaces( "\t", 2 ) ).isEqualTo( "  " );
        assertThat( tabsToSpaces( "\t", 4 ) ).isEqualTo( "    " );
    }

    @Test
    public void testTabsToSpaces_tabWidthCanBeNegative() {
        assertThat( tabsToSpaces( " \t", Integer.MIN_VALUE ) ).isEqualTo( " " );
        assertThat( tabsToSpaces( " \t", -1024 ) ).isEqualTo( " " );
        assertThat( tabsToSpaces( " \t", -1 ) ).isEqualTo( " " );
        assertThat( tabsToSpaces( " \t", 0 ) ).isEqualTo( " " );
    }
}
