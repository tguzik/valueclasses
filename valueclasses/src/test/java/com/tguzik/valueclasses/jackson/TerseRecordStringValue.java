package com.tguzik.valueclasses.jackson;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;

@NullMarked
record TerseRecordStringValue(String value) implements TerseStringValue {
  TerseRecordStringValue {
    Objects.requireNonNull( value );
  }
}
