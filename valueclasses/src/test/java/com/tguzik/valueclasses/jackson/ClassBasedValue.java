package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.value.Value;
import org.jspecify.annotations.NullMarked;

/**
 * This class doesn't follow the naming pattern to avoid a clash with {@link java.lang.ClassValue}
 */
@NullMarked
class ClassBasedValue extends Value<Long> {

  @JsonCreator
  public ClassBasedValue( final long value ) {
    super( value );
  }

  @Override
  @JsonValue
  public Long get() {
    return super.get();
  }
}
