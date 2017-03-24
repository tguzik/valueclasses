package com.tguzik.value;

import javax.annotation.Nullable;
import java.util.Objects;

import com.tguzik.traits.HasValue;
import org.apache.commons.lang3.StringUtils;

/**
 * Base abstract class for wrappers on values allowing to give them their own
 * type. This class and its subclasses are meant to be immutable by themselves -
 * not allowing to change the reference to held value. This class cannot give
 * any guarantees about the value itself.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @see com.tguzik.value.adapters.JaxbValueAdapter
 * @since 0.1
 */
public abstract class Value<T> implements HasValue<T> {
    @Nullable
    private final T encapsulatedValue;

    protected Value( @Nullable T encapsulatedValue ) {
        this.encapsulatedValue = encapsulatedValue;
    }

    @Nullable
    @Override
    public T get() {
        return encapsulatedValue;
    }

    @Override
    public int hashCode() {
        final T localValue = get();

        if ( localValue != null ) {
            return localValue.hashCode();
        }

        return 0;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }

        // Before somebody starts shouting "but why not instanceof?" - we want to treat different types at NOT equal.
        // See this example:
        // - Speed(42.0f)         (extends Value<Float>)
        // - Temperature(42.0f)   (extends Value<Float>)
        //
        // Even though the contained value is the same, the type implies very different meaning.
        if ( !Objects.equals( this.getClass(), obj.getClass() ) ) {
            return false;
        }

        final Value<?> other = (Value<?>) obj;
        return Objects.equals( this.get(), other.get() );
    }

    @Override
    public String toString() {
        final T localValue = get();

        if ( localValue != null ) {
            return localValue.toString();
        }

        return StringUtils.EMPTY;
    }
}
