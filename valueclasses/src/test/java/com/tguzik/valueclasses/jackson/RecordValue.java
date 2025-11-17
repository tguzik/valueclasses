package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasValue;
import org.jspecify.annotations.NullMarked;

@NullMarked
record RecordValue(long value) implements HasValue<Long> {

  @JsonCreator
  RecordValue {
    // Compact constructor defined to allow Jackson annotation to be placed
  }

  @Override
  @JsonValue
  public Long get() {
    return value;
  }
}
