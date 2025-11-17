package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
record NullableRecordStringValue(@Nullable String value) implements HasStringValue {

  @JsonCreator
  NullableRecordStringValue {
    // Compact constructor defined to allow Jackson annotation to be placed
  }

  @Override
  @Nullable
  @JsonValue
  public String get() {
    return value;
  }
}
