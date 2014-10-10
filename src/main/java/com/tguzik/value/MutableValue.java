package com.tguzik.value;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Provides a mutable value class that uses AtomicReference<T> as a base. Extends Value<T> and shadows the actual
 * value only to allow casting instances of this class to Value<T>.
 * <p/>
 * Use of this class is heavily discouraged, however it will be kept around as a last-resort workaround for
 * architectures that go against any sane design. Instead of forcing everyone to hack around what they are working
 * on, I opt to give a least horrible way to deal with the problem. Temporarily.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.2
 */
@Deprecated
public abstract class MutableValue<T> extends Value<T> {
    /* This is a hacky way to work around generic types not allowing to pass AtomicReference<> itself to superclass.
     * If we passed the AtomicReference object to superclass, method {@link #get()} would have to return object of 
     * type AtomicReference<T> instead of T.
     * 
     * As long as the superclass keeps using method {@link #get()} instead of directly referencing the field, we 
     * should be fine.
     */
    private final AtomicReference<T> value;

    protected MutableValue( T value ) {
        super( null );
        this.value = new AtomicReference<>( value );
    }

    @Nullable
    @Override
    public T get() {
        return value.get();
    }

    public void set( @Nullable T newValue ) {
        value.set( newValue );
    }
}
