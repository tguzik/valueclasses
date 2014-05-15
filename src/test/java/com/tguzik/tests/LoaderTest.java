package com.tguzik.tests;

import static com.tguzik.tests.Loader.loadFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class LoaderTest
{
    @Test
    public void testLoadFile( ) throws IOException {
        String fileContents = loadFile( "src/test/java", getClass(), "data", "test-file.txt" );

        assertNotNull( fileContents );
        assertEquals( 20, fileContents.length() );
        assertEquals( "Test file contents\n\n", fileContents );
    }

    @Test
    public void testLoadFile_allFunctionsLoadSameFile( ) throws IOException {
        // Load data using all four methods
        String fileContentsUsingPath = loadFile( Paths.get( "src/test/java/com/tguzik/tests/data/test-file.txt" ) );
        String fileContentsUsingPrefixClassSubdirFname = loadFile( "src/test/java",
                                                                   getClass(),
                                                                   "data",
                                                                   "test-file.txt" );
        String fileContentsUsingClassSubdirFilename = loadFile( getClass(), "data", "test-file.txt" );
        String fileContentsUsingPrefixDirSubdirFilename = loadFile( "src/test/java",
                                                                    "com/tguzik/tests",
                                                                    "data",
                                                                    "test-file.txt" );

        // Verify that all variables hold exact same data
        assertEquals( fileContentsUsingPath, fileContentsUsingClassSubdirFilename );
        assertEquals( fileContentsUsingPath, fileContentsUsingPrefixClassSubdirFname );
        assertEquals( fileContentsUsingPath, fileContentsUsingPrefixDirSubdirFilename );

        // Verify that the loaded data is correct
        assertEquals( "Test file contents\n\n", fileContentsUsingPath );
    }
}
