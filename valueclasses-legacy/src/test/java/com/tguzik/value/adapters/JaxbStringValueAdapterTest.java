package com.tguzik.value.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;
import java.util.stream.Stream;

import com.tguzik.objects.BaseObject;
import com.tguzik.traits.HasValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.assertj3.XmlAssert;

class JaxbStringValueAdapterTest {

  static Stream<Arguments> serializationTestCases() {
    return Stream.of( Arguments.of( new FakeContainer(), """
                                                         <FakeContainer>
                                                             <integerValue>0</integerValue>
                                                             <stringValue />
                                                         </FakeContainer>
                                                         """ ),
                      Arguments.of( new FakeContainer( 123, null, "some string" ), """
                                                                                   <FakeContainer>
                                                                                       <integerValue>123</integerValue>
                                                                                       <stringValue>some string</stringValue>
                                                                                   </FakeContainer>
                                                                                   """ ),
                      Arguments.of( new FakeContainer( 123, new FakeValue( "some string" ), "different string" ), """
                                                                                                                  <FakeContainer>
                                                                                                                      <integerValue>123</integerValue>
                                                                                                                      <valueBasedClass>some string</valueBasedClass>
                                                                                                                      <stringValue>different string</stringValue>
                                                                                                                  </FakeContainer>
                                                                                                                  """ ) );
  }

  @ParameterizedTest
  @MethodSource( "serializationTestCases" )
  void container_with_value_based_class_using_adapter_serializes_to_expected_xml( final FakeContainer input,
                                                                                  final String expectedXml )
  throws JAXBException {
    final var context = JAXBContext.newInstance( FakeContainer.class, FakeValue.class );
    final var stringWriter = new StringWriter();
    context.createMarshaller().marshal( input, stringWriter );

    final String actualXml = stringWriter.toString();

    XmlAssert.assertThat( actualXml ).describedAs( "actual:\n" + actualXml ).and( expectedXml ).ignoreWhitespace().areIdentical();
  }

  @ParameterizedTest
  @MethodSource( "serializationTestCases" )
  void value_based_class_can_be_deserialized_from_xml_using_adapter( final FakeContainer expected, final String input )
  throws JAXBException {
    final var context = JAXBContext.newInstance( FakeContainer.class, FakeValue.class );
    final var source = new StreamSource( new StringReader( input ) );
    final FakeContainer actual = context.createUnmarshaller().unmarshal( source, FakeContainer.class ).getValue();

    assertThat( actual ).usingRecursiveComparison().isEqualTo( expected );
    assertThat( actual ).isEqualTo( expected );
  }

  @XmlAccessorType( XmlAccessType.FIELD )
  @XmlRootElement( name = "FakeContainer" )
  static class FakeContainer extends BaseObject {
    @XmlElement( name = "integerValue" )
    int integerValue;

    @XmlElement( name = "valueBasedClass" )
    FakeValue valueBasedClass;

    @XmlElement( name = "stringValue" )
    String stringValue;

    public FakeContainer() {
      this( 0, null, "" );
    }

    public FakeContainer( int integerValue, FakeValue valueBasedClass, String stringValue ) {
      this.integerValue = integerValue;
      this.valueBasedClass = valueBasedClass;
      this.stringValue = stringValue;
    }
  }

  @XmlJavaTypeAdapter( Adapter.class )
  record FakeValue(String value) implements HasValue<String> {
    @Override
    public String get() {
      return value;
    }
  }

  static class Adapter extends JaxbStringValueAdapter<FakeValue> {
    @Override
    protected FakeValue createNewInstance( String value ) {
      if ( Objects.isNull( value ) ) {
        return null;
      }
      return new FakeValue( value );
    }
  }
}
