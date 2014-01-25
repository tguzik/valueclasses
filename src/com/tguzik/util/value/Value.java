package com.tguzik.util.value;

import java.util.Objects;

import javax.annotation.Nullable;

import com.tguzik.util.annotations.ReadOnly;
import com.tguzik.util.traits.HasValue;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public abstract class Value< T > implements HasValue<T>
{
    protected final T value;

    protected Value( @Nullable T value ) {
        this.value = value;
    }

    @ReadOnly
    @Nullable
    @Override
    public T getValue( ) {
        return value;
    }

    @Override
    public int hashCode( ) {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString( ) {
        return String.format( "%s(%s)", getClass().getName(), value );
    }

    @Override
    public boolean equals( @Nullable Object obj ) {
        if ( obj != null && getClass().isAssignableFrom( obj.getClass() ) ) {
            Value<?> other = (Value<?>) obj;

            return Objects.equals( this.value, other.value );
        }

        return false;
    }
}
