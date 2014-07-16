package com.tguzik.value;

import java.util.Objects;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import com.tguzik.traits.HasValue;
import com.tguzik.value.adapters.AbstractJaxbValueAdapter;

/**
 * <p>
 * Base abstract class for wrappers on values allowing to give them their own
 * type. This class and its subclasses are meant to be immutable by themselves -
 * not allowing to change the reference to held value. This class cannot give
 * any guarantees about the value itself.
 * </p>
 * <p>
 * If your project uses JaxB, it is recommended for descendants of this class
 * are annotated with {@link XmlJavaTypeAdapter}, where the annotation specifies
 * {@link XmlJavaTypeAdapter#value()} to be a descendant of
 * {@link AbstractJaxbValueAdapter}
 * </p>
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public abstract class Value< T > implements HasValue<T>
{
    private final T value;

    protected Value( @Nullable T value ) {
        this.value = value;
    }

    @Nullable
    @Override
    public T get( ) {
        return value;
    }

    @Override
    public int hashCode( ) {
        T localValue = get();
        return localValue != null ? localValue.hashCode() : 0;
    }

    @Override
    public boolean equals( @Nullable Object obj ) {
        if ( obj != null && isSameClassOrDescendant( obj.getClass() ) ) {
            Value<?> other = (Value<?>) obj;

            return Objects.equals( this.get(), other.get() );
        }

        return false;
    }

    @Override
    public String toString( ) {
        T localValue = get();
        return localValue != null ? localValue.toString() : StringUtils.EMPTY;
    }

    private boolean isSameClassOrDescendant( Class<?> other ) {
        return this.getClass().isAssignableFrom( other );
    }
}
