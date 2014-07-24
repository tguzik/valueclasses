package com.tguzik.tests;

import static com.tguzik.tests.Normalize.newLines;
import static com.tguzik.tests.Normalize.tabsToSpaces;
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
        assertEquals( (String) null, newLines( null ) );
    }

    @Test
    public void testNewLines_emptyString() {
        assertEquals( "", newLines( "" ) );
    }

    @Test
    public void testNewLines_stringWithoutWhitespaceNotChanged() {
        assertEquals( "abc", newLines( "abc" ) );
    }

    @Test
    public void testTabsToSpaces() {
        assertEquals( "a", tabsToSpaces( "a", 4 ) );
        assertEquals( "    a    ", tabsToSpaces( "\ta\t", 4 ) );
        assertEquals( "a     ", tabsToSpaces( "a \t", 4 ) );
        assertEquals( "    a\r\nx    \r\n", tabsToSpaces( "\ta\r\nx\t\r\n", 4 ) );
    }

    @Test
    public void testTabsToSpaces_nullGiven_nullReturned() {
        assertEquals( (String) null, tabsToSpaces( null, 4 ) );
    }

    @Test
    public void testTabsToSpaces_emptyString() {
        assertEquals( "", tabsToSpaces( "", 4 ) );
    }

    @Test
    public void testTabsToSpaces_differentTabWidths() {
        assertEquals( "", tabsToSpaces( "\t", Integer.MIN_VALUE ) );
        assertEquals( "", tabsToSpaces( "\t", -1024 ) );
        assertEquals( "", tabsToSpaces( "\t", -1 ) );
        assertEquals( "", tabsToSpaces( "\t", 0 ) );
        assertEquals( " ", tabsToSpaces( "\t", 1 ) );
        assertEquals( "  ", tabsToSpaces( "\t", 2 ) );
        assertEquals( "    ", tabsToSpaces( "\t", 4 ) );
    }

    @Test
    public void testTabsToSpaces_tabWidthCanBeNegative() {
        assertEquals( " ", tabsToSpaces( " \t", Integer.MIN_VALUE ) );
        assertEquals( " ", tabsToSpaces( " \t", -1024 ) );
        assertEquals( " ", tabsToSpaces( " \t", -1 ) );
        assertEquals( " ", tabsToSpaces( " \t", 0 ) );
    }
}
