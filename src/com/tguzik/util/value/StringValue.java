package com.tguzik.util.value;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO: Documentation
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Immutable
public abstract class StringValue extends Value<String> implements Comparable<StringValue>
{
    protected StringValue( @Nullable String value ) {
        super( value );
    }

    @Override
    public int compareTo( @Nullable StringValue other ) {
        return ( value != null && other != null && other.value != null ) ? value.compareTo( other.value ) : 0;
    }

    public int length( ) {
        return value != null ? value.length() : 0;
    }

    public boolean isEmpty( ) {
        return length() == 0;
    }

    public boolean isBlank( ) {
        return StringUtils.isBlank( value );
    }
}
