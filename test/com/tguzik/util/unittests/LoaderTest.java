package com.tguzik.util.unittests;

import static com.tguzik.util.unittests.Loader.loadFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class LoaderTest
{
    // TODO: Write more unit tests

    @Test
    public void testLoadFile( ) throws IOException {
        String fileContents = loadFile( "test", getClass(), "data", "test-file.txt" );

        assertNotNull( fileContents );
        assertEquals( 20, fileContents.length() );
        assertEquals( "Test file contents\n\n", fileContents );
    }
}
