package com.tguzik.valueclasses.jackson;

import java.util.Locale;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
enum EnumStringValue implements HasStringValue {
  FIRST( "ABC" ),
  SECOND( "BCD" ),
  THIRD( "DEF" );

  private final String value;

  EnumStringValue( final String value ) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String get() {
    return value;
  }

  public static Optional<EnumStringValue> fromString( @Nullable final String input ) {
    final String trimmed = StringUtils.trimToEmpty( input ).toUpperCase( Locale.ROOT );
    for ( final EnumStringValue entry : values() ) {
      if ( entry.value.equals( trimmed ) ) {
        return Optional.of( entry );
      }
    }
    return Optional.empty();
  }

  @Nullable
  @JsonCreator
  public static EnumStringValue jacksonForValue( @Nullable final String input ) {
    return fromString( input ).orElse( null );
  }
}
