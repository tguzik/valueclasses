package com.tguzik.tests;

import javax.annotation.concurrent.Immutable;

import com.tguzik.value.Value;

/**
 * Allows returning pre-set value in <code>.hashCode()</code> calls.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Immutable
public final class SettableHashCode extends Value<Integer>
{
    private SettableHashCode( Integer value ) {
        super( value );
    }

    @Override
    public int hashCode( ) {
        return get();
    }

    public static SettableHashCode create( int desiredHashCode ) {
        return new SettableHashCode( desiredHashCode );
    }
}
