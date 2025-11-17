package com.tguzik.value.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.tguzik.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JaxbValueAdapterTest {
  private TestingAdapter adapterReturningInstances;
  private TestingAdapter adapterReturningNulls;

  @BeforeEach
  void setUp() {
    adapterReturningInstances = new TestingAdapter( true );
    adapterReturningNulls = new TestingAdapter( false );
  }

  @Test
  void testUnmarshal_createNewInstanceReturnsInstances() throws Exception {
    FakeValue value = adapterReturningInstances.unmarshal( "string" );

    assertThat( value ).isNotNull();
    assertThat( value.get() ).isEqualTo( "string" );
  }

  @Test
  void testUnmarshal_createNewInstanceReturnsNulls() throws Exception {
    FakeValue value = adapterReturningNulls.unmarshal( "string" );

    assertThat( value ).isNull();
  }

  @Test
  void testMarshal() throws Exception {
    assertThat( adapterReturningInstances.marshal( new FakeValue( "string" ) ) ).isEqualTo( "string" );
    assertThat( adapterReturningNulls.marshal( new FakeValue( "string" ) ) ).isEqualTo( "string" );
  }

  @Test
  void testMarshal_nullValueClass() throws Exception {
    assertThat( adapterReturningInstances.marshal( null ) ).isNull();
    assertThat( adapterReturningNulls.marshal( null ) ).isNull();
  }

  @Test
  void testMarshal_nullValueInValueClass() throws Exception {
    assertThat( adapterReturningInstances.marshal( new FakeValue( null ) ) ).isNull();
    assertThat( adapterReturningNulls.marshal( new FakeValue( null ) ) ).isNull();
  }

  static class TestingAdapter extends JaxbValueAdapter<String, FakeValue> {
    private final boolean shouldReturnInstance;

    public TestingAdapter( boolean shouldReturnInstance ) {
      this.shouldReturnInstance = shouldReturnInstance;
    }

    @Override
    protected FakeValue createNewInstance( String value ) {
      return (shouldReturnInstance ? new FakeValue( value ) : null);
    }
  }

  static class FakeValue extends StringValue {
    public FakeValue( String value ) {
      super( value );
    }
  }
}
