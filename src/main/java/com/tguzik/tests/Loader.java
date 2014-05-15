package com.tguzik.tests;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Unit test utility that loads files. Primary use case is reading text files
 * and using the contents as expected values in unit tests. This is placed in
 * main sources (not test sources) to allow using this class in projects that
 * depend on this library.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public class Loader
{
    @Nullable
    public static String loadFile( @Nonnull Path path ) throws IOException {
        byte[] fileBytes = java.nio.file.Files.readAllBytes( path );
        String contents = new String( fileBytes, Charset.forName( "UTF-8" ) );
        return Normalize.newLines( contents );
    }

    @Nullable
    public static String loadFile( @Nonnull String prefix,
                                   @Nonnull Class<?> classFromPackage,
                                   @Nonnull String subdirectory,
                                   @Nonnull String fileName ) throws IOException {
        String classPackage = classFromPackage.getPackage().getName().replace( ".", "/" );
        return loadFile( Paths.get( prefix, classPackage, subdirectory, fileName ) );
    }

    @Nullable
    public static String loadFile( @Nonnull String prefix,
                                   @Nonnull String directory,
                                   @Nonnull String subdirectory,
                                   @Nonnull String fileName ) throws IOException {
        return loadFile( Paths.get( prefix, directory, subdirectory, fileName ) );
    }

    @Nullable
    /** Assumes that the prefix will be 'src/test/java/' */
    public static String loadFile( @Nonnull Class<?> classFromPackage,
                                   @Nonnull String subdirectory,
                                   @Nonnull String fileName ) throws IOException {
        return loadFile( "src/test/java", classFromPackage, subdirectory, fileName );
    }
}
