package com.tguzik.value.adapters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.tguzik.value.Value;

/**
 * JaxB adapter template for value classes in this package. The user is expected
 * to subclass one of the descendants of this class, implementing method
 * {@link #createNewInstance(Object)}. The method is expected to create new
 * instance of the value class of expected type.
 *
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 * @since 0.2
 */
@ParametersAreNullableByDefault
public abstract class JaxbValueAdapter<UnderlyingType, ValueClass extends Value<UnderlyingType>>
        extends XmlAdapter<UnderlyingType, ValueClass> {

    @Override
    @Nonnull
    public ValueClass unmarshal( @Nullable final UnderlyingType value ) throws Exception {
        return createNewInstance( value );
    }

    @Override
    @Nullable
    public UnderlyingType marshal( @Nullable final ValueClass valueClass ) throws Exception {
        if ( valueClass == null ) {
            return null;
        }

        return valueClass.get();
    }

    /**
     * Creates new instance of correct value class with argument as the
     * contained value. It is not recommended for implementations of this method
     * to return `null` values.
     *
     * @param value the value of type {@code UnderlyingType} to be encapsulated
     * @return instance of the {@code ValueClass} containing the encapsulated value
     */
    @Nonnull
    protected abstract ValueClass createNewInstance( @Nullable UnderlyingType value );
}
