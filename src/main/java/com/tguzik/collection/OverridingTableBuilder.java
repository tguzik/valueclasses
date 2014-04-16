package com.tguzik.collection;

import static com.tguzik.collection.Safe.safe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.builder.Builder;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.tguzik.annotations.ExpectedFailureProfile;
import com.tguzik.annotations.ReadOnly;
import com.tguzik.annotations.ExpectedFailureProfile.FailureMode;
import com.tguzik.annotations.ExpectedFailureProfile.Transactional;

/**
 * TODO: Documentation
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @see {@link OverridingImmutableTableBuilder}
 * @since 0.1
 */
@NotThreadSafe
@ExpectedFailureProfile( value = FailureMode.FAIL_FAST, transactional = Transactional.NO )
public final class OverridingTableBuilder< R, C, V > implements Builder<Table<R, C, V>>
{
    private final Table<R, C, V> backingMap;

    private OverridingTableBuilder( @ReadOnly @Nullable Table<R, C, V> initializeWithValuesFrom ) {
        this.backingMap = HashBasedTable.create( safe( initializeWithValuesFrom ) );
    }

    /**
     * Puts the <tt>value</tt> under <tt>row</tt> and <tt>key</tt> into the
     * backing table, overriding any value that might have been stored there.
     */
    public OverridingTableBuilder<R, C, V> overrideWith( @Nonnull R row, @Nonnull C column, @Nonnull V value ) {
        backingMap.put( row, column, value );
        return this;
    }

    /**
     * Puts all keys and values from <tt>table</tt> into the backing store,
     * overriding any values that has been stored under same rows and columns as
     * in the passed parameter.
     */
    public OverridingTableBuilder<R, C, V> overrideWith( @ReadOnly @Nullable Table<? extends R, ? extends C, ? extends V> table ) {
        backingMap.putAll( safe( table ) );
        return this;
    }

    /**
     * Returns a <i>mutable copy</i> of the backing table. This method can be
     * safely called multiple times and each built result will <i>not</i> modify
     * the others.
     */
    @Override
    public Table<R, C, V> build( ) {
        return HashBasedTable.create( backingMap );
    }

    public static < R, C, V > OverridingTableBuilder<R, C, V> create( ) {
        return create( ImmutableTable.<R, C, V> of() );
    }

    /**
     * @param initializeWithValuesFrom
     *            Initialize the builder with values from this map. The map is
     *            treated as read-only and is neither stored nor modified
     *            throughout the lifetime of the builder.
     */
    public static < R, C, V > OverridingTableBuilder<R, C, V> create( @ReadOnly @Nullable Table<R, C, V> initializeWithValuesFrom ) {
        return new OverridingTableBuilder<>( initializeWithValuesFrom );
    }
}
