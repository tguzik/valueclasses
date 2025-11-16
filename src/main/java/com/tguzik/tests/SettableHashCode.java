package com.tguzik.tests;

import java.util.Objects;

import com.tguzik.value.Value;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Allows returning pre-set value in <code>.hashCode()</code> calls.
 *
 * @author Tomasz Guzik
 * @since 0.1
 */
@NullMarked
public final class SettableHashCode extends Value<Integer> {
  public SettableHashCode( final int value ) {
    super( value );
  }

  @Override
  public int hashCode() {
    return get();
  }

  @Override
  public boolean equals( @Nullable final Object obj ) {
    if ( obj == null ) {
      return false;
    }
    if ( this == obj ) {
      return true;
    }

    if ( obj instanceof SettableHashCode other ) {
      // Class is final, so we don't have to check for child classes.
      return Objects.equals( this.get(), other.get() );
    }

    return false;
  }

  @Deprecated
  public static SettableHashCode create( final int desiredHashCode ) {
    return new SettableHashCode( desiredHashCode );
  }
}
