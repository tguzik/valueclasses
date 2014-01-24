package com.tguzik.util.interfaces;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.Builder;

/**
 * Indicates that the builder has ability to copy existing objects.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public interface CopyingBuilder< T > extends Builder<T>
{
    @Nonnull
    CopyingBuilder<T> copyOf( @Nonnull T existing );
}
