package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.value.Value;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
class NullableClassValue extends Value<Long> {

  @JsonCreator
  public NullableClassValue( @Nullable final Long value ) {
    super( value );
  }

  @Override
  @Nullable
  @JsonValue
  public Long get() {
    return super.get();
  }
}
