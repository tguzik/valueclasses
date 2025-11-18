package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;

interface TerseStringValue extends HasStringValue {
  @Override
  @JsonValue
  default String get() {
    return value();
  }

  String value();
}
