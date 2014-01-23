package com.tguzik.util.interfaces;

/**
 * Indicates that the builder has ability to copy existing objects.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public interface CopyingBuilder< T > extends Builder<T>
{
    CopyingBuilder<T> copyOf( T existing );
}
