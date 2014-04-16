package com.tguzik.collection;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.tguzik.annotations.ReadOnly;

/**
 * TODO: Documentation
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public final class Safe
{
    /**
     * Ensures that the parameter <tt>value</tt> will not cause
     * NullPointerExceptions when attempting to read from it. Recommended to use
     * in for-each loops when attempting to iterate over a collection from
     * unreliable source, like ORM framework. The returned value should be
     * treated as read-only.
     * 
     * @param value
     *            Nullable map.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty {@link ImmutableMap}.
     */
    @ReadOnly
    @Nonnull
    public static < K, V > Map<K, V> safe( @Nullable Map<K, V> value ) {
        return value == null ? ImmutableMap.<K, V> of() : value;
    }

    /**
     * Ensures that the parameter <tt>value</tt> will not cause
     * NullPointerExceptions when attempting to read from it. Recommended to use
     * in for-each loops when attempting to iterate over a collection from
     * unreliable source, like ORM framework. The returned value should be
     * treated as read-only.
     * 
     * @param value
     *            Nullable list.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty {@link ImmutableList}.
     */
    @ReadOnly
    @Nonnull
    public static < T > Iterable<T> safe( @Nullable Iterable<T> value ) {
        return value == null ? ImmutableList.<T> of() : value;
    }

    /**
     * Ensures that the parameter <tt>value</tt> will not cause
     * NullPointerExceptions when attempting to read from it. Recommended to use
     * in for-each loops when attempting to iterate over a collection from
     * unreliable source, like ORM framework. The returned value should be
     * treated as read-only.
     * 
     * @param value
     *            Nullable list.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty {@link ImmutableList}.
     */
    @ReadOnly
    @Nonnull
    public static < T > Collection<T> safe( @Nullable Collection<T> value ) {
        return value == null ? ImmutableList.<T> of() : value;
    }

    /**
     * Ensures that the parameter <tt>value</tt> will not cause
     * NullPointerExceptions when attempting to read from it. Recommended to use
     * in for-each loops when attempting to iterate over a collection from
     * unreliable source, like ORM framework. The returned value should be
     * treated as read-only.
     * 
     * @param value
     *            Nullable list.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty {@link ImmutableList}.
     */
    @ReadOnly
    @Nonnull
    public static < T > List<T> safe( @Nullable List<T> value ) {
        return value == null ? ImmutableList.<T> of() : value;
    }

    /**
     * Ensures that the parameter <tt>value</tt> will not cause
     * NullPointerExceptions when attempting to read from it. Recommended to use
     * in for-each loops when attempting to iterate over a collection from
     * unreliable source, like ORM framework. The returned value should be
     * treated as read-only.
     * 
     * @param value
     *            Nullable set.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty {@link ImmutableSet}.
     */
    @ReadOnly
    @Nonnull
    public static < T > Set<T> safe( @Nullable Set<T> value ) {
        return value == null ? ImmutableSet.<T> of() : value;
    }

    /**
     * Ensures that the parameter <tt>value</tt>will not cause
     * NullPointerExceptions when attempting to read from it. Recommended to use
     * in for-each loops when attempting to iterate over a collection from
     * unreliable source, like ORM framework. The returned value should be
     * treated as read-only.
     * 
     * @param value
     *            Nullable table.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty {@link ImmutableTable}.
     */
    @ReadOnly
    @Nonnull
    public static < R, C, V > Table<R, C, V> safe( @Nullable Table<R, C, V> value ) {
        return value == null ? ImmutableTable.<R, C, V> of() : value;
    }

    private Safe() {
        // Private constructor to ensure that the class cannot me instantiated.
    }
}
