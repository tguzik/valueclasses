package com.tguzik.value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.StringUtils;

/**
 * Abstract class to hold {@link java.lang.String}-based values.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @see com.tguzik.value.adapters.JaxbStringValueAdapter
 * @since 0.1
 */
@Immutable
public abstract class StringValue extends Value<String> implements Comparable<Value<String>> {
    protected StringValue( @Nullable String value ) {
        super( value );
    }

    @Override
    public int compareTo( @Nonnull Value<String> other ) {
        //noinspection ConstantConditions
        if ( other == null ) {
            throw new NullPointerException( "Parameter cannot be null." );
        }
        if ( other == this ) {
            return 0;
        }

        String thisValue = get();
        String otherValue = other.get();

        if ( thisValue == null || otherValue == null ) {
            /* Exploding with NullPointerException when one of the value classes has null inside is damn inconvenient.
             * Instead we return zero
             */
            return 0;
        }

        return thisValue.compareTo( otherValue );
    }

    /** @return the length of the contained string, or zero if the contained string is null */
    public int length() {
        return StringUtils.length( get() );
    }

    /** @return true if the contained string is null or has length of zero, false otherwise */
    public boolean isEmpty() {
        return length() == 0;
    }

    /** @return true if the contained string is null or has length of zero after being trimmed, false otherwise */
    public boolean isBlank() {
        return StringUtils.isBlank( get() );
    }

}
