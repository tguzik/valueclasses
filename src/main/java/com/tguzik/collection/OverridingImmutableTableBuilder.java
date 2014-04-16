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
 * @see {@link OverridingTableBuilder}
 * @since 0.1
 */
@NotThreadSafe
@ExpectedFailureProfile( value = FailureMode.FAIL_FAST, transactional = Transactional.NO )
public final class OverridingImmutableTableBuilder< R, C, V > implements Builder<ImmutableTable<R, C, V>>
{
    private final Table<R, C, V> backingBuilder;

    private OverridingImmutableTableBuilder( @ReadOnly @Nullable Table<R, C, V> initializeWithValuesFrom ) {
        this.backingBuilder = HashBasedTable.create( safe( initializeWithValuesFrom ) );
    }

    /**
     * Puts the <tt>value</tt> under <tt>row</tt> and <tt>key</tt> into the
     * backing table, overriding any value that might have been stored there.
     */
    public OverridingImmutableTableBuilder<R, C, V> overrideWith( @Nonnull R row,
                                                                  @Nonnull C column,
                                                                  @Nonnull V value ) {
        backingBuilder.put( row, column, value );
        return this;
    }

    /**
     * Puts all keys and values from <tt>table</tt> into the backing store,
     * overriding any values that has been stored under same rows and columns as
     * in the passed parameter.
     */
    public OverridingImmutableTableBuilder<R, C, V> overrideWith( @ReadOnly @Nullable Table<? extends R, ? extends C, ? extends V> table ) {
        backingBuilder.putAll( safe( table ) );
        return this;
    }

    /**
     * Returns a <i>immutable</i> table with values put by the user. This method
     * can be safely called multiple times and each built result will <i>not</i>
     * modify the others.
     */
    @Override
    public ImmutableTable<R, C, V> build( ) {
        return ImmutableTable.copyOf( backingBuilder );
    }

    public static < R, C, V > OverridingImmutableTableBuilder<R, C, V> create( ) {
        return create( ImmutableTable.<R, C, V> of() );
    }

    /**
     * @param initializeWithValuesFrom
     *            Initialize the builder with values from this table. The table
     *            is treated as read-only and is neither stored nor modified
     *            throughout the lifetime of the builder.
     */
    public static < R, C, V > OverridingImmutableTableBuilder<R, C, V> create( @ReadOnly @Nullable Table<R, C, V> initializeWithValuesFrom ) {
        return new OverridingImmutableTableBuilder<>( initializeWithValuesFrom );
    }
}
