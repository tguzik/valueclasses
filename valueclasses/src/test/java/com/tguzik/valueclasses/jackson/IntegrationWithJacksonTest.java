package com.tguzik.valueclasses.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.tguzik.traits.HasValue;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.databind.json.JsonMapper;

class IntegrationWithJacksonTest {

  static Stream<Arguments> serializationTestCases() {
    return Stream.of( Arguments.of( new SampleContainer(), //
                                    """
                                    {
                                      "field01": null,
                                      "field02": null,
                                      "field03": null,
                                      "field04": null,
                                      "field05": null,
                                      "field06": null,
                                      "field07": null,
                                      "field08": null,
                                      "field09": null,
                                      "field10": null,
                                      "field11": null
                                    }
                                    """ ),
                      Arguments.of( new SampleContainer( new ClassStringValue( "value one" ),
                                                         new ClassBasedValue( 123 ),
                                                         EnumStringValue.SECOND,
                                                         EnumValue.THIRD,
                                                         new NullableClassStringValue( null ),
                                                         new NullableClassValue( null ),
                                                         new NullableRecordStringValue( null ),
                                                         new NullableRecordValue( null ),
                                                         new RecordStringValue( "  value nine  " ),
                                                         new RecordValue( 1234 ),
                                                         new TerseRecordStringValue( "value eleven" ) ), //
                                    """
                                    {
                                      "field01": "value one",
                                      "field02": 123,
                                      "field03": "BCD",
                                      "field04": 345,
                                      "field05": null,
                                      "field06": null,
                                      "field07": null,
                                      "field08": null,
                                      "field09": "VALUE NINE",
                                      "field10": 1234,
                                      "field11": "value eleven"
                                    }
                                    """ ),
                      Arguments.of( new SampleContainer( new ClassStringValue( "value one" ),
                                                         new ClassBasedValue( 123 ),
                                                         EnumStringValue.SECOND,
                                                         EnumValue.THIRD,
                                                         new NullableClassStringValue( "value five" ),
                                                         new NullableClassValue( 678L ),
                                                         new NullableRecordStringValue( "value seven" ),
                                                         new NullableRecordValue( 890L ),
                                                         new RecordStringValue( "  value nine  " ),
                                                         new RecordValue( 1234 ),
                                                         new TerseRecordStringValue( "value eleven" ) ), //
                                    """
                                    {
                                      "field01": "value one",
                                      "field02": 123,
                                      "field03": "BCD",
                                      "field04": 345,
                                      "field05": "value five",
                                      "field06": 678,
                                      "field07": "value seven",
                                      "field08": 890,
                                      "field09": "VALUE NINE",
                                      "field10": 1234,
                                      "field11": "value eleven"
                                    }
                                    """ ) );
  }

  @ParameterizedTest
  @MethodSource( "serializationTestCases" )
  void jackson_can_serialize_container_to_expected_json( final SampleContainer input, final String expectedJson ) {
    final var mapper = JsonMapper.builder().build();
    final String actual = mapper.writeValueAsString( input );

    assertThat( actual ).asInstanceOf( JsonAssertions.JSON )
                        .describedAs( "actual:\n---\n" + actual + "\n---\n" )
                        .isEqualTo( expectedJson );
  }

  @ParameterizedTest
  @MethodSource( "serializationTestCases" )
  void jackson_can_deserialize_json_back_into_container( final SampleContainer expected, final String input ) {
    final var mapper = JsonMapper.builder().build();
    final SampleContainer actual = mapper.readValue( input, SampleContainer.class );

    assertThat( actual ).usingRecursiveComparison()
                        .withStrictTypeChecking()
                        .withEqualsForType( IntegrationWithJacksonTest::compareByContainedValue, NullableClassStringValue.class )
                        .withEqualsForType( IntegrationWithJacksonTest::compareByContainedValue, NullableClassValue.class )
                        .withEqualsForType( IntegrationWithJacksonTest::compareByContainedValue, NullableRecordStringValue.class )
                        .withEqualsForType( IntegrationWithJacksonTest::compareByContainedValue, NullableRecordValue.class )
                        .isEqualTo( expected );
  }

  /**
   * This is a bit of a hack - during deserialization Jackson by default will prefer setting a field to null instead of
   * calling the method designated by {@link com.fasterxml.jackson.annotation.JsonCreator}
   */
  private static boolean compareByContainedValue( final HasValue<?> x, final HasValue<?> y ) {
    return Objects.equals( Optional.ofNullable( x ).map( HasValue::get ).orElse( null ),
                           Optional.ofNullable( y ).map( HasValue::get ).orElse( null ) );
  }
}
