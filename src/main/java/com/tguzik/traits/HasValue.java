package com.tguzik.traits;

/**
 * Indicates that the class has single value of specified type. Typically
 * implemented by data classes.
 *
 * @author Tomasz Guzik
 * @since 0.1
 */
public interface HasValue<T> {
  T get();
}
