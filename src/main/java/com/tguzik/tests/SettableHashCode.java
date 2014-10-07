package com.tguzik.tests;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

import com.tguzik.value.Value;

/**
 * Allows returning pre-set value in <code>.hashCode()</code> calls.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Immutable
@ParametersAreNonnullByDefault
public final class SettableHashCode extends Value<Integer> {
    private SettableHashCode( Integer value ) {
        super( value );
    }

    @Override
    public int hashCode() {
        return get();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof SettableHashCode ) {
            SettableHashCode other = (SettableHashCode) obj;
            return Objects.equals( this.get(), other.get() );
        }

        return false;
    }

    public static SettableHashCode create( int desiredHashCode ) {
        return new SettableHashCode( desiredHashCode );
    }
}
