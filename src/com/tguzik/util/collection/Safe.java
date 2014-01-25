package com.tguzik.util.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.tguzik.util.annotations.ReadOnly;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public final class Safe
{
    /**
     * Ensures that the iterated collection will not cause NullPointerExceptions
     * when attempting to read from it. Recommended to use in for-each loops
     * when attempting to iterate over a collection from unreliable source, like
     * ORM framework. The returned value should be read-only.
     * 
     * @param value
     *            Nullable collection.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty immutable list.
     */
    @ReadOnly
    @Nonnull
    public static < K, V > Map<K, V> safe( @Nullable Map<K, V> value ) {
        return value == null ? ImmutableMap.<K, V> of() : value;
    }

    /**
     * Ensures that the iterated collection will not cause NullPointerExceptions
     * when attempting to read from it. Recommended to use in for-each loops
     * when attempting to iterate over a collection from unreliable source, like
     * ORM framework. The returned value should be read-only.
     * 
     * @param value
     *            Nullable collection.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty immutable list.
     */
    @ReadOnly
    @Nonnull
    public static < T > List<T> safe( @Nullable List<T> value ) {
        return value == null ? ImmutableList.<T> of() : value;
    }

    /**
     * Ensures that the iterated collection will not cause NullPointerExceptions
     * when attempting to read from it. Recommended to use in for-each loops
     * when attempting to iterate over a collection from unreliable source, like
     * ORM framework. The returned value should be read-only.
     * 
     * @param value
     *            Nullable collection.
     * @return <tt>value</tt> if the passed argument was not null. Otherwise
     *         returns an empty immutable list.
     */
    @ReadOnly
    @Nonnull
    public static < T > Set<T> safe( @Nullable Set<T> value ) {
        return value == null ? ImmutableSet.<T> of() : value;
    }
}
