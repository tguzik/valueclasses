package com.tguzik.value.adapters;

import com.tguzik.traits.HasValue;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Alternate version of {@link JaxbValueAdapter} that relies on the {@code jakarta.xml.*} namespace.
 *
 * @since 2.0
 */
@NullMarked
public abstract class JakartaJaxbValueAdapter<T, C extends HasValue<T>> extends XmlAdapter<T, C> {

  @Override
  public C unmarshal( @Nullable final T value ) throws Exception {
    return createNewInstance( value );
  }

  @Override
  @Nullable
  public T marshal( @Nullable final C valueClass ) throws Exception {
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
  protected abstract C createNewInstance( @Nullable T value );
}
