package com.tguzik.tests;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

import com.tguzik.value.Value;

/**
 * Allows returning pre-set value in <code>.hashCode()</code> calls.
 *
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 * @since 0.1
 */
@Immutable
@ParametersAreNonnullByDefault
public final class SettableHashCode extends Value<Integer> {
    public SettableHashCode( final int value ) {
        super( value );
    }

    @Override
    public int hashCode() {
        return get();
    }

    @Override
    public boolean equals( final Object obj ) {
        if ( obj == null ) {
            return false;
        }

        if ( this == obj ) {
            return true;
        }

        if ( obj instanceof SettableHashCode ) {
            // Class is final, so we don't have to check for child classes.
            final SettableHashCode other = (SettableHashCode) obj;
            return Objects.equals( this.get(), other.get() );
        }

        return false;
    }

    @Deprecated
    public static SettableHashCode create( final int desiredHashCode ) {
        return new SettableHashCode( desiredHashCode );
    }
}
