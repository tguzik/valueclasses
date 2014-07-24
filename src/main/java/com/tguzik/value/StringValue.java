package com.tguzik.value;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.StringUtils;

/**
 * Abstract class to hold {@link java.lang.String}-based values.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Immutable
public abstract class StringValue extends Value<String> implements Comparable<StringValue> {
    protected StringValue( @Nullable String value ) {
        super( value );
    }

    @Override
    public int compareTo( @Nullable StringValue other ) {
        String otherValue = (other == null) ? null : other.get();
        String thisValue = get();

        return (thisValue == null || otherValue == null) ? 0 : thisValue.compareTo( otherValue );
    }

    public int length() {
        return StringUtils.length( get() );
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    public boolean isBlank() {
        return StringUtils.isBlank( get() );
    }
}
