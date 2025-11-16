package com.tguzik.value;

import java.util.Objects;

import com.tguzik.traits.HasValue;
import org.apache.commons.lang3.StringUtils;

/**
 * Base abstract class for wrappers on values allowing to give them their own
 * type. This class and its subclasses are meant to be immutable by themselves -
 * not allowing to change the reference to held value. This class cannot give
 * any guarantees about the value itself.
 *
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 * @see com.tguzik.value.adapters.JaxbValueAdapter
 * @since 0.1
 */
public abstract class Value<T> implements HasValue<T> {
  private final T encapsulatedValue;

  protected Value( final T encapsulatedValue ) {
    this.encapsulatedValue = encapsulatedValue;
  }

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
  @SuppressWarnings( "EqualsGetClass" )
  public boolean equals( final Object obj ) {
    if ( obj == null ) {
      return false;
    }
    if ( obj == this ) {
      return true;
    }

    // Remember that we want to treat different types as NOT equal, for example:
    // - Speed(42.0f)         (extends Value<Float>)
    // - Temperature(42.0f)   (extends Value<Float>)
    //
    // Even though they contain the same value, the type implies very different meaning.
    //
    // The only reason why we are not changing it to use `this.getClass().isAssignableFrom( obj.getClass() )`
    // by default again, is that it would subtly break the behaviour that existed since 2014. I'll add an
    // optional(-ish) constructor parameter to switch between behaviours, defaulting to the one that existed for
    // the past five years, just to avoid breaking random applications.
    //
    // Perhaps with a major version number bump we could change the default, but considering that Records are to be
    // available in JDK14, I doubt we would even see a next major version of this library :/
    //
    // TODO: New constructor argument to change the behaviour. Change BaseObject#equals() as well, as it is has a
    //  similar story
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
