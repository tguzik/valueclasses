package com.tguzik.value.adapters;

import com.tguzik.value.StringValue;

/**
 * Convenience class for typed values where the underlying type is a string.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.3
 */
public abstract class AbstractStringValueAdapter<ValueClass extends StringValue>
        extends AbstractJaxbValueAdapter<String, ValueClass> {
    // Nothing defined.
}
