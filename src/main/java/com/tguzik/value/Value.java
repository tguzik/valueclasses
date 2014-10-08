package com.tguzik.value;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

import com.tguzik.traits.HasValue;
import com.tguzik.value.adapters.AbstractJaxbValueAdapter;
import org.apache.commons.lang3.StringUtils;

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
public abstract class Value<T> implements HasValue<T> {
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
