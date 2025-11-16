package com.tguzik.traits;

import org.jspecify.annotations.NullMarked;

/**
 * Indicates that the class has single value of specified type. Typically
 * implemented by data classes.
 *
 * @author Tomasz Guzik
 * @since 0.1
 */
@NullMarked
public interface HasValue<T> {
  T get();
}
