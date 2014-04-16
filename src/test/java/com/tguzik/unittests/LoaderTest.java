package com.tguzik.unittests;

import static com.tguzik.unittests.Loader.loadFile;
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
        String fileContents = loadFile( "src/test/java", getClass(), "data", "test-file.txt" );

        assertNotNull( fileContents );
        assertEquals( 20, fileContents.length() );
        assertEquals( "Test file contents\n\n", fileContents );
    }
}
