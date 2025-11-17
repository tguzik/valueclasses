package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasValue;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
record NullableRecordValue(@Nullable Long value) implements HasValue<Long> {

  @JsonCreator
  NullableRecordValue {
    // Compact constructor defined to allow Jackson annotation to be placed
  }

  @Override
  @Nullable
  @JsonValue
  public Long get() {
    return value;
  }
}
