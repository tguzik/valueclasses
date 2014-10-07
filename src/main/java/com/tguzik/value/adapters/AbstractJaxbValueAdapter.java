package com.tguzik.value.adapters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.tguzik.value.Value;

/**
 * JaxB adapter template for value classes in this package. The user is expected
 * to subclass one of the descendants of this class, implementing method
 * {@link #createNewInstance(Object)}. The method is expected to create new
 * instance of the value class of expected type.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.2
 */
public abstract class AbstractJaxbValueAdapter<UnderlyingType, ValueClass extends Value<UnderlyingType>>
        extends XmlAdapter<UnderlyingType, ValueClass> {
    @Override
    public ValueClass unmarshal( @Nullable UnderlyingType value ) throws Exception {
        return createNewInstance( value );
    }

    @Override
    public UnderlyingType marshal( @Nullable ValueClass valueClass ) throws Exception {
        return (valueClass == null) ? null : valueClass.get();
    }

    /**
     * Creates new instance of correct value class with argument as the
     * contained value. It is not recommended for implementations of this method
     * to return `null` values.
     */
    @Nonnull
    protected abstract ValueClass createNewInstance( @Nullable UnderlyingType value );
}
