package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.value.Value;
import org.jspecify.annotations.NullMarked;

@NullMarked
class ClassValue extends Value<Long> {

  @JsonCreator
  public ClassValue( final long value ) {
    super( value );
  }

  @Override
  @JsonValue
  public Long get() {
    return super.get();
  }
}
