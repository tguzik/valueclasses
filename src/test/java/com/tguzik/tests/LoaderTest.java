package com.tguzik.tests;

import static com.tguzik.tests.Loader.loadFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class LoaderTest {
    @Test
    public void testLoadFile() throws IOException {
        String fileContents = loadFile( "src/test/java", getClass(), "data", "test-file.txt" );

        assertThat( fileContents ).isNotNull().hasSize( 20 ).isEqualTo( "Test file contents\n\n" );
    }

    @Test
    public void testLoadFile_allFunctionsLoadSameFile() throws IOException {
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

        assertThat( fileContentsUsingPath ).isEqualTo( fileContentsUsingClassSubdirFilename )
                                           .isEqualTo( fileContentsUsingPrefixClassSubdirFname )
                                           .isEqualTo( fileContentsUsingPrefixDirSubdirFilename )
                                           .isEqualTo( "Test file contents\n\n" );
    }
}
