package com.tguzik.util.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public class TestUtils
{
    /**
     * Loads file from current directory.
     * 
     * @see TestUtils#loadFile(String, String)
     */
    @Nullable
    public static String loadFile( @Nonnull String fileName ) throws IOException {
        return loadFile(".", fileName);
    }

    /**
     * Loads file from specified path and normalizes line endings to make it
     * suitable for testing with {@link Assert#assertEquals}.
     * 
     * @param path
     *            Relative or absolute path to the directory. If empty string is
     *            passed then it defaults to absolute path. Not nullable.
     * @path fileName Name of the file to be loaded. Not nullable.
     */
    @Nullable
    public static String loadFile(@Nonnull String path,  @Nonnull String fileName ) throws IOException {
        File file = Files.getFile(path, "/", fileName);
        String contents = com.google.common.io.Files..readFileToString(file, Charset.forName("UTF-8"));

        return normalizeNewLines(contents);
    }

    /**
     * Replaces different line ending styles (Windows \r\n, Mac \r) with single
     * new line character (\n).
     */
    @Nullable
    public static String normalizeNewLines( @Nullable String input ) {
        return input == null ? null : input.replaceAll("(\r)?\n(\r)?", "\n").replaceAll("\r", "\n");
    }

}
