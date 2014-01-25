package com.tguzik.util.collection;

import static com.tguzik.util.collection.Safe.safe;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.builder.Builder;

import com.google.common.collect.ImmutableMap;
import com.tguzik.util.annotations.ReadOnly;

/**
 * This class attempts to make it more <i>clean</i> and <i>readable</i> to
 * create <i>immutable</i> map C with values from map A, overwritten by values
 * from map B. An instance of {@link ImmutableMap.Builder} is used as the
 * backing builder.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @see {@link OverridingMapBuilder}
 * @since 0.1
 */
@NotThreadSafe
public final class OverridingImmutableMapBuilder< K, V > implements Builder<ImmutableMap<K, V>>
{
    private final ImmutableMap.Builder<K, V> backingBuilder;

    private OverridingImmutableMapBuilder( @ReadOnly @Nullable Map<K, V> initializeWithValuesFrom ) {
        this.backingBuilder = ImmutableMap.<K, V> builder();
        overrideWith( initializeWithValuesFrom );
    }

    /**
     * Puts <tt>key</tt> and <tt>value</tt> into the backing map, overriding any
     * value that has been stored under <tt>key</tt>.
     */
    public OverridingImmutableMapBuilder<K, V> overrideWith( @Nonnull K key, @Nonnull V value ) {
        backingBuilder.put( key, value );
        return this;
    }

    /**
     * Puts all keys and values from <tt>map</tt> into the backing map,
     * overriding any values that has been stored under same keys as in
     * <tt>map</tt> parameter. If the parameter is null, no action is taken.
     */
    public OverridingImmutableMapBuilder<K, V> overrideWith( @ReadOnly @Nullable Map<? extends K, ? extends V> map ) {
        backingBuilder.putAll( safe( map ) );
        return this;
    }

    /**
     * Returns the <i>immutable</i> map with values put by the user. This method
     * can be safely called multiple times and each built result will <i>not</i>
     * modify the others.
     */
    @Override
    public ImmutableMap<K, V> build( ) {
        return backingBuilder.build();
    }

    public static < K, V > OverridingImmutableMapBuilder<K, V> create( ) {
        return create( ImmutableMap.<K, V> of() );
    }

    /**
     * @param initializeWithValuesFrom
     *            Initialize the builder with values from this map. The map is
     *            treated as read-only and is neither stored nor modified
     *            throughout the lifetime of the builder.
     */
    public static < K, V > OverridingImmutableMapBuilder<K, V> create( @ReadOnly @Nonnull Map<K, V> initializeWithValuesFrom ) {
        return new OverridingImmutableMapBuilder<>( initializeWithValuesFrom );
    }
}
