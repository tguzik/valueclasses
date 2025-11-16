package com.tguzik.tests;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit test utility that loads files. Primary use case is reading text files
 * and using the contents as expected values in unit tests. This is placed in
 * main sources (not test sources) to allow using this class in projects that
 * depend on this library.
 * <br>
 * Loaded files have their newline characters normalized to single '\n' character
 * using {@link com.tguzik.tests.Normalize#newLines(String)}. Loaded files are
 * not trimmed - whitespace at the beginning and the end of the file will be
 * preserved.
 *
 * @author Tomasz Guzik
 * @since 0.1
 */
@ParametersAreNonnullByDefault
public enum Loader {
  ;

  @Nullable
  public static String loadFile( final Path path ) throws IOException {
    final byte[] fileBytes = java.nio.file.Files.readAllBytes( path );
    final String contents = new String( fileBytes, StandardCharsets.UTF_8 );
    return Normalize.newLines( contents );
  }

  @Nullable
  public static String loadFile( final String prefix,
                                 final Class<?> classFromPackage,
                                 final String subdirectory,
                                 final String fileName ) throws IOException {
    final String classPackage = classFromPackage.getPackage().getName().replace( ".", "/" );
    return loadFile( Paths.get( prefix, classPackage, subdirectory, fileName ) );
  }

  @Nullable
  public static String loadFile( final String prefix, final String directory, final String subdirectory, final String fileName )
  throws IOException {
    return loadFile( Paths.get( prefix, directory, subdirectory, fileName ) );
  }

  /**
   * Assumes that the prefix will be 'src/test/java/'
   */
  @Nullable
  public static String loadFile( final Class<?> classFromPackage, final String subdirectory, final String fileName )
  throws IOException {
    return loadFile( "src/test/java", classFromPackage, subdirectory, fileName );
  }
}
