package com.tguzik.tests;

import static com.tguzik.tests.Loader.loadFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 */
public class LoaderTest {
    @Test
    public void loadFile_returns_the_content_of_given_file() throws IOException {
        String fileContents = loadFile( "src/test/java", getClass(), "data", "test-file.txt" );

        assertThat( fileContents ).isNotNull().hasSize( 20 ).isEqualTo( "Test file contents\n\n" );
    }

    @Test
    public void loadFile_all_function_variants_return_the_same_content() throws IOException {
        // Load data using all four methods
        final String usingPath = loadFile( Paths.get( "src/test/java/com/tguzik/tests/data/test-file.txt" ) );
        final String usingPrefixClassSubdirAndFname = loadFile( "src/test/java", getClass(), "data", "test-file.txt" );
        final String usingClassSubdirAndFilename = loadFile( getClass(), "data", "test-file.txt" );
        final String usingPrefixDirSubdirAndFilename = loadFile( "src/test/java",
                                                                 "com/tguzik/tests",
                                                                 "data",
                                                                 "test-file.txt" );

        assertThat( usingPath ).isEqualTo( usingClassSubdirAndFilename )
                               .isEqualTo( usingPrefixClassSubdirAndFname )
                               .isEqualTo( usingPrefixDirSubdirAndFilename )
                               .isEqualTo( "Test file contents\n\n" );
    }
}
