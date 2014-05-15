package com.tguzik.value;

import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;

/**
 * Provides a mutable value class that uses AtomicReference<T> as a base.
 * 
 * This class extends Value<T> and shadows the actual value only to allow
 * casting instances of this class to Value<T>.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.2
 */
public class MutableValue< T > extends Value<T>
{
    private final AtomicReference<T> value;

    protected MutableValue( T value ) {
        super( null );
        this.value = new AtomicReference<>( value );
    }

    @Nullable
    @Override
    public T get( ) {
        return value.get();
    }

    public void set( @Nullable T newValue ) {
        value.set( newValue );
    }
}
