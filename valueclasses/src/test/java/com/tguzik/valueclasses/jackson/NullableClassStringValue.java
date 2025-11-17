package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.value.StringValue;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
class NullableClassStringValue extends StringValue {

  @JsonCreator
  public NullableClassStringValue( @Nullable final String value ) {
    super( value );
  }

  @Override
  @Nullable
  @JsonValue
  public String get() {
    return super.get();
  }
}
