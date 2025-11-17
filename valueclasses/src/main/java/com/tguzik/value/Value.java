package com.tguzik.value;

import java.util.Objects;

import com.tguzik.traits.HasValue;
import org.jspecify.annotations.NullMarked;

/**
 * Base abstract class for wrappers on values allowing to give them their own type. This class and its subclasses are meant to
 * be immutable by themselves - not allowing to change the reference to held value. This class cannot give any guarantees about
 * the value itself.
 * <p>
 * Note that with version 2.x of this library the preference is to leverage Java Records instead of this class. This class is
 * still here for backward compatibility.
 *
 * @author Tomasz Guzik
 * @since 0.1
 */
@NullMarked
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

    if ( Objects.isNull( localValue ) ) {
      // This shouldn't happen, but better safe than sorry
      return 0;
    }

    return localValue.hashCode();
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
    // The only reason why we are using `instanceof` is that it would subtly break the behaviour that existed since 2014.
    if ( !Objects.equals( this.getClass(), obj.getClass() ) ) {
      return false;
    }

    final Value<?> other = (Value<?>) obj;
    return Objects.equals( this.get(), other.get() );
  }

  @Override
  public String toString() {
    final T localValue = get();

    if ( Objects.isNull( localValue ) ) {
      // This shouldn't happen, but better safe than sorry
      return "";
    }

    return localValue.toString();
  }

}
