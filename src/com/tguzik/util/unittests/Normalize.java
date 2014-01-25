package com.tguzik.util.unittests;

import javax.annotation.Nullable;

import com.google.common.annotations.Beta;

/**
 * TODO: Documentation
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Beta
public class Normalize
{
    /**
     * <p>
     * Replaces different line ending styles (Windows <code>\r\n</code>, Mac
     * <code>\r</code>) with single new line character (<code>\n</code>).
     * </p>
     * 
     * Example usage:
     * 
     * <pre>
     * assertEquals( expected, Normalize.newLines( actual ) );
     * </pre>
     */
    @Nullable
    public static String newLines( @Nullable String input ) {
        return input == null ? null : input.replaceAll( "(\r)?\n(\r)?", "\n" ).replaceAll( "\r", "\n" );
    }

    // TODO: Perhaps a new method to normalize tabs (\t) would be useful?
}
