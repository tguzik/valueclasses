package com.tguzik.value.adapters;

import com.tguzik.value.StringValue;
import org.jspecify.annotations.NullMarked;

/**
 * Convenience class for typed values where the underlying type is a string.
 *
 * @author Tomasz Guzik
 * @since 0.3
 */
@NullMarked
public abstract class JaxbStringValueAdapter<ValueClass extends StringValue> extends JaxbValueAdapter<String, ValueClass> {
  // Nothing defined.
}
