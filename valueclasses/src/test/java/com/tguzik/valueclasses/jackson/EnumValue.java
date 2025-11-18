package com.tguzik.valueclasses.jackson;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasValue;
import org.jspecify.annotations.Nullable;

enum EnumValue implements HasValue<Long> {
  FIRST( 123 ),
  SECOND( 234 ),
  THIRD( 345 );

  private final long value;

  EnumValue( final long value ) {
    this.value = value;
  }

  @Override
  @JsonValue
  public Long get() {
    return value;
  }

  public Optional<EnumValue> fromLong( final long input ) {
    for ( final var entry : values() ) {
      if ( entry.value == input ) {
        return Optional.of( entry );
      }
    }
    return Optional.empty();
  }

  @Nullable
  @JsonCreator
  public EnumValue jacksonForValue( final long input ) {
    return fromLong( input ).orElse( null );
  }
}
