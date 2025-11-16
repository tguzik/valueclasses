package com.tguzik.value.adapters;

import com.tguzik.value.StringValue;

/**
 * Convenience class for typed values where the underlying type is a string.
 *
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 * @since 0.3
 */
public abstract class JaxbStringValueAdapter<ValueClass extends StringValue> extends JaxbValueAdapter<String, ValueClass> {
  // Nothing defined.
}
