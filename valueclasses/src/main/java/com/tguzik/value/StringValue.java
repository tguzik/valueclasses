package com.tguzik.value;

import java.util.Objects;

import com.tguzik.traits.HasStringValue;
import org.jspecify.annotations.NullMarked;

/**
 * Abstract class to hold {@link java.lang.String}-based values.
 * <p>
 * Note that with version 2.x of this library the preference is to leverage Java Records instead of this class. This class is
 * still here for backward compatibility.
 *
 * @author Tomasz Guzik
 * @since 0.1
 */
@NullMarked
@SuppressWarnings( "PMD.OverrideBothEqualsAndHashCodeOnComparable" )
public abstract class StringValue extends Value<String> implements HasStringValue, Comparable<StringValue> {

  protected StringValue( final String value ) {
    super( value );
  }

  @Override
  public int compareTo( final StringValue other ) {
    Objects.requireNonNull( other, "Parameter cannot be null." );

    final String thisValue = get();
    final String otherValue = other.get();

    if ( thisValue == null || otherValue == null ) {
      // Exploding with NullPointerException when one of the value classes has null inside is damn inconvenient.
      // Let's return zero instead
      return 0;
    }

    return thisValue.compareTo( otherValue );
  }
}
