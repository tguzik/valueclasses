package com.tguzik.value.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.tguzik.traits.HasValue;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * JaxB adapter template for value classes in this package. The user is expected
 * to subclass one of the descendants of this class, implementing method
 * {@link #createNewInstance(Object)}. The method is expected to create new
 * instance of the value class of expected type.
 *
 * @see <a href="https://eclipse-foundation.blog/2019/05/03/jakarta-ee-java-trademarks/">Migration from javax to jakarta ns</a>
 * @since 0.2
 * @deprecated Relies on the {@code javax.xml.*} namespace
 */
@NullMarked
public abstract class JaxbValueAdapter<T, C extends HasValue<T>> extends XmlAdapter<T, C> {

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
