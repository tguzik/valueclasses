package com.tguzik.value.adapters;

import static org.junit.Assert.*;

import com.tguzik.value.StringValue;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class AbstractJaxbValueAdapterTest {
    private TestingAdapter adapterReturningInstances;
    private TestingAdapter adapterReturningNulls;

    @Before
    public void setUp() throws Exception {
        adapterReturningInstances = new TestingAdapter( true );
        adapterReturningNulls = new TestingAdapter( false );
    }

    @Test
    public void testUnmarshal_createNewInstanceReturnsInstances() throws Exception {
        TestingValueClass value = adapterReturningInstances.unmarshal( "string" );

        assertNotNull( value );
        assertEquals( "string", value.get() );
    }

    @Test
    public void testUnmarshal_createNewInstanceReturnsNulls() throws Exception {
        TestingValueClass value = adapterReturningNulls.unmarshal( "string" );

        assertNull( value );
    }

    @Test
    public void testMarshal() throws Exception {
        assertEquals( "string", adapterReturningInstances.marshal( new TestingValueClass( "string" ) ) );
        assertEquals( "string", adapterReturningNulls.marshal( new TestingValueClass( "string" ) ) );
    }

    @Test
    public void testMarshal_nullValueClass() throws Exception {
        assertNull( adapterReturningInstances.marshal( null ) );
        assertNull( adapterReturningNulls.marshal( null ) );
    }

    @Test
    public void testMarshal_nullValueInValueClass() throws Exception {
        assertNull( adapterReturningInstances.marshal( new TestingValueClass( null ) ) );
        assertNull( adapterReturningNulls.marshal( new TestingValueClass( null ) ) );
    }

    static class TestingAdapter extends AbstractJaxbValueAdapter<String, TestingValueClass> {
        private final boolean shouldReturnInstance;

        public TestingAdapter( boolean shouldReturnInstance ) {
            this.shouldReturnInstance = shouldReturnInstance;
        }

        @Override
        protected TestingValueClass createNewInstance( String value ) {
            return (shouldReturnInstance ? new TestingValueClass( value ) : null);
        }
    }

    static class TestingValueClass extends StringValue {
        public TestingValueClass( String value ) {
            super( value );
        }
    }
}
