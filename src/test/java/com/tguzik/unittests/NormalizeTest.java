package com.tguzik.unittests;

import static com.tguzik.unittests.Normalize.newLines;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class NormalizeTest
{
    @Test
    public void testNewLines( ) {
        assertEquals( "abc   \n  ", newLines( "abc   \r\n\r  " ) );
        assertEquals( "  \n \nabc  \n", newLines( "  \n \nabc  \r" ) );
        assertEquals( "\n\n\n", newLines( "\n\n\n" ) );
        assertEquals( "\n\n", newLines( "\r\n\r\n\r" ) );
    }

    @Test
    public void testNewLines_nullGiven_nullReturned( ) {
        assertEquals( (String) null, newLines( null ) );
    }

    @Test
    public void testNewLines_emptyString( ) {
        assertEquals( "", newLines( "" ) );
    }

    @Test
    public void testNewLines_stringWithoutWhitespaceNotChanged( ) {
        assertEquals( "abc", newLines( "abc" ) );
    }
}
