package com.tguzik.tests;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

/**
 * Unit test utility that normalizes Windows (\r\n) and legacy OSX (\r) line
 * endings into unix-style (\n). This is placed in main sources (as opposed to
 * unit test sources) to allow using this class in projects that depend on this
 * library.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public enum Normalize {
    ;

    /**
     * <p>
     * Replaces different line ending styles (Windows <code>\r\n</code>, legacy
     * Mac <code>\r</code>) with single new line character (<code>\n</code>).
     * </p>
     * <p/>
     * Example usage:
     * <p/>
     * <pre>
     * assertEquals( expected, Normalize.newLines( actual ) );
     * </pre>
     *
     * @param input
     *         nullable input string
     *
     * @return new string with all newline sequences replaced with LF ending
     */
    @Nullable
    public static String newLines( @Nullable String input ) {
        return input == null ? null : input.replaceAll( "(\r)?\n(\r)?", "\n" ).replaceAll( "\r", "\n" );
    }

    /**
     * <p>
     * Replaces all tabulation (<code>\t</code>) characters with spaces.
     * </p>
     *
     * @param input
     *         nullable input string
     * @param tabWidth
     *         width in spaces of each tabulation character
     *
     * @return new string with all tabulation characters replaces by tabs or
     * null if input parameter was null
     */
    @Nullable
    public static String tabsToSpaces( @Nullable String input, int tabWidth ) {
        int spacesInSingleTab = (tabWidth < 0) ? 0 : tabWidth;
        String replacement = StringUtils.repeat( ' ', spacesInSingleTab );
        return input == null ? null : input.replaceAll( "\t", replacement );
    }
}
