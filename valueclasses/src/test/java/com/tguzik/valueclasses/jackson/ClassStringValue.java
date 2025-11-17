package com.tguzik.valueclasses.jackson;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.value.StringValue;
import org.jspecify.annotations.NullMarked;

@NullMarked
class ClassStringValue extends StringValue {

  @JsonCreator
  public ClassStringValue( final String value ) {
    super( Objects.requireNonNull( value ) );
  }

  @Override
  @JsonValue
  public String get() {
    return super.get();
  }
}
