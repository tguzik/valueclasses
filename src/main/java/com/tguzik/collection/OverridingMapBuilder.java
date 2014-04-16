package com.tguzik.collection;

import static com.tguzik.collection.Safe.safe;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.builder.Builder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.tguzik.annotations.ExpectedFailureProfile;
import com.tguzik.annotations.ReadOnly;
import com.tguzik.annotations.ExpectedFailureProfile.FailureMode;
import com.tguzik.annotations.ExpectedFailureProfile.Transactional;

/**
 * This class attempts to make it more <i>clean</i> and <i>readable</i> to
 * create <i>mutable</i> map C with values from map A, overwritten by values
 * from map B. An instance of {@link HashMap} will be used as the backing map.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @see {@link OverridingImmutableMapBuilder}
 * @since 0.1
 */
@NotThreadSafe
@ExpectedFailureProfile( value = FailureMode.FAIL_FAST, transactional = Transactional.NO )
public final class OverridingMapBuilder< K, V > implements Builder<Map<K, V>>
{
    private final Map<K, V> backingMap;

    private OverridingMapBuilder( @ReadOnly @Nonnull Map<K, V> initializeWithValuesFrom ) {
        this.backingMap = Maps.newHashMap( safe( initializeWithValuesFrom ) );
    }

    /**
     * Puts <tt>key</tt> and <tt>value</tt> into the backing map, overriding any
     * value that has been stored under <tt>key</tt>.
     */
    public OverridingMapBuilder<K, V> overrideWith( @Nullable K key, @Nullable V value ) {
        backingMap.put( key, value );
        return this;
    }

    /**
     * Puts all keys and values from <tt>map</tt> into the backing map,
     * overriding any values that has been stored under same keys as in
     * <tt>map</tt> parameter. If the parameter is null, no action is taken.
     */
    public OverridingMapBuilder<K, V> overrideWith( @ReadOnly @Nullable Map<? extends K, ? extends V> map ) {
        backingMap.putAll( safe( map ) );
        return this;
    }

    /**
     * Returns a <i>mutable copy</i> of the backing map. This method can be
     * safely called multiple times and each built result will <i>not</i> modify
     * the others.
     */
    @Override
    public Map<K, V> build( ) {
        return Maps.newHashMap( backingMap );
    }

    public static < K, V > OverridingMapBuilder<K, V> create( ) {
        return create( ImmutableMap.<K, V> of() );
    }

    /**
     * @param initializeWithValuesFrom
     *            Initialize the builder with values from this map. The map is
     *            treated as read-only and is neither stored nor modified
     *            throughout the lifetime of the builder.
     */
    public static < K, V > OverridingMapBuilder<K, V> create( @ReadOnly @Nullable Map<K, V> initializeWithValuesFrom ) {
        return new OverridingMapBuilder<>( initializeWithValuesFrom );
    }
}
