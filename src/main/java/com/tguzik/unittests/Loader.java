package com.tguzik.unittests;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.google.common.io.Files;
import com.tguzik.annotations.RefactorThis;

/**
 * TODO: Documentation
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Beta
@RefactorThis( "Clean up these static methods - not all are needed" )
public class Loader
{
    @Nullable
    public static String loadFile( @Nonnull Path path ) throws IOException {
        String contents = Files.toString( path.toFile(), Charset.forName( "UTF-8" ) );
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
}
