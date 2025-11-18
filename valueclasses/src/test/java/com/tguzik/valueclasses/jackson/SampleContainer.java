package com.tguzik.valueclasses.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.jspecify.annotations.NullUnmarked;

@NullUnmarked
@JsonRootName( "SampleContainer" )
record SampleContainer(@JsonProperty( "field01" ) ClassStringValue classBasedStringValue,
                       @JsonProperty( "field02" ) ClassBasedValue classBasedLongValue,
                       @JsonProperty( "field03" ) EnumStringValue enumStringValue,
                       @JsonProperty( "field04" ) EnumValue enumLongValue,
                       @JsonProperty( "field05" ) NullableClassStringValue classNullOrStringValue,
                       @JsonProperty( "field06" ) NullableClassValue classNullOrLongValue,
                       @JsonProperty( "field07" ) NullableRecordStringValue recordNullOrStringValue,
                       @JsonProperty( "field08" ) NullableRecordValue recordNullOrLongValue,
                       @JsonProperty( "field09" ) RecordStringValue recordStringValue,
                       @JsonProperty( "field10" ) RecordValue recordLongValue,
                       @JsonProperty( "field11" ) TerseRecordStringValue terseStringValue) {

  // Truth be told it doesn't really need the JsonRootName and JsonProperty annotations to do what we need it to do, but let's
  // create a complete example.

  SampleContainer() {
    this( null, null, null, null, null, null, null, null, null, null, null );
  }
}
