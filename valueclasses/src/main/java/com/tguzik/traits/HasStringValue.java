package com.tguzik.traits;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;

/**
 * Specialization of the {@link HasValue} interface for values based on regular {@link String}s. This interface provides
 * similar functionality as the <em>class</em> {@link com.tguzik.value.StringValue}, however it can be applied to Java Records.
 *
 * @since 2.0.0
 */
@NullMarked
@FunctionalInterface
public interface HasStringValue extends HasValue<String> {

  /**
   * @return the length of the contained string, or zero if the contained string is null
   */
  default int length() {
    final String value = get();
    return Objects.isNull( value ) ? 0 : value.length();
  }

  /**
   * @return true if the contained string is null or has length of zero, false otherwise
   */
  default boolean isEmpty() {
    final String value = get();
    return Objects.isNull( value ) || value.isEmpty();
  }

  /**
   * @return true if the contained string is null or has length of zero after being trimmed, false otherwise
   */
  default boolean isBlank() {
    final String value = get();
    return Objects.isNull( value ) || value.isBlank();
  }
}
