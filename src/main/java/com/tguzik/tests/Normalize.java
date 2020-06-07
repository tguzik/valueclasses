package com.tguzik.tests;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

/**
 * Unit test utility that normalizes Windows (\r\n) and legacy OSX (\r) line
 * endings into unix-style (\n). This is placed in main sources (as opposed to
 * unit test sources) to allow using this class in projects that depend on this
 * library.
 *
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 * @since 0.1
 */
public enum Normalize {
    ;

    /**
     * Replaces different line ending styles (Windows <code>\r\n</code>, legacy
     * Mac <code>\r</code>) with single new line character (<code>\n</code>).
     * <br>
     * Example usage:
     * <pre>
     * assertEquals( expected, Normalize.newLines( actual ) );
     * </pre>
     *
     * @param input nullable input string
     * @return new string with all newline sequences replaced with LF ending
     */
    @Nullable
    public static String newLines( @Nullable final String input ) {
        if ( input == null ) {
            return null;
        }

        return input.replaceAll( "(\r)?\n(\r)?", "\n" ) //
                    .replaceAll( "\r", "\n" );
    }

    /**
     * Replaces all tabulation (<code>\t</code>) characters with spaces.
     *
     * @param input    nullable input string
     * @param tabWidth width in spaces of each tabulation character
     * @return If input was not null, returns new string with all tabulation characters replaces by spaces. If input
     * was null, returns null.
     */
    @Nullable
    public static String tabsToSpaces( @Nullable final String input, final int tabWidth ) {
        if ( input == null ) {
            return null;
        }

        final int spacesInSingleTab = Math.max( tabWidth, 0 );
        final String replacement = StringUtils.repeat( ' ', spacesInSingleTab );
        return input.replaceAll( "\t", replacement );
    }
}
