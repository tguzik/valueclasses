package com.tguzik.util.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public final class CollectionUtil
{
    public static < K, V > Map<K, V> safe( Map<K, V> value ) {
        return value == null ? ImmutableMap.<K, V> of() : value;
    }

    public static < T > List<T> safe( List<T> value ) {
        return value == null ? ImmutableList.<T> of() : value;
    }

    public static < T > Set<T> safe( Set<T> value ) {
        return value == null ? ImmutableSet.<T> of() : value;
    }

    public static < K, V > ImmutableMap<K, V> copyToImmutableMap( Map<K, V> value ) {
        return value == null ? ImmutableMap.<K, V> of() : ImmutableMap.copyOf(safe(value));
    }

    public static < T > ImmutableList<T> copyToImmutableList( List<T> value ) {
        return value == null ? ImmutableList.<T> of() : ImmutableList.copyOf(safe(value));
    }

    public static < T > ImmutableSet<T> copyToImmutableSet( Set<T> value ) {
        return value == null ? ImmutableSet.<T> of() : ImmutableSet.copyOf(safe(value));
    }
}
