package com.tguzik.valueclasses.jackson;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;
import org.jspecify.annotations.NullMarked;

@NullMarked
record RecordStringValue(String value) implements HasStringValue {

  @JsonCreator
  RecordStringValue {
    Objects.requireNonNull( value );
  }

  @Override
  @JsonValue
  public String get() {
    return value;
  }
}
