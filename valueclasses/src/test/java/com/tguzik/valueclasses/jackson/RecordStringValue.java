package com.tguzik.valueclasses.jackson;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
record RecordStringValue(String value) implements HasStringValue {

  @JsonCreator
  RecordStringValue( @Nullable final String value ) {
    // Optional normalization/transformation:
    this.value = StringUtils.trimToEmpty( value ).toUpperCase( Locale.ROOT );
  }

  @Override
  @JsonValue
  public String get() {
    return value;
  }
}
