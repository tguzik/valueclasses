package com.tguzik.util.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import com.tguzik.util.unittests.SettableHashCode;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class ValueTest
{
    private Value<?> valueContainingNull;
    private Value<?> value;

    private String containedValue;

    @Before
    public void setUp( ) throws Exception {
        containedValue = "some value";

        valueContainingNull = ValueTestHelper.create( null );
        value = ValueTestHelper.create( containedValue );
    }

    @Test
    public void testGetValue( ) {
        assertSame( containedValue, value.getValue() );
    }

    @Test
    public void testGetValue_null( ) {
        assertNull( valueContainingNull.getValue() );
    }

    @Test
    public void testHashCode( ) {
        assertEquals( 123, ValueTestHelper.create( SettableHashCode.create( 123 ) ).hashCode() );
    }

    @Test
    public void testHashCode_null( ) {
        assertEquals( 0, valueContainingNull.hashCode() );
    }

    @Test
    public void testToString( ) {
        assertEquals( "ValueTestHelper(some value)", value.toString() );
    }

    @Test
    public void testToString_null( ) {
        assertEquals( "ValueTestHelper(null)", valueContainingNull.toString() );
    }

    @Test
    public void testEquals_equal_sameClassAndContainedValue( ) {
        assertEquals( value, ValueTestHelper.create( containedValue ) );
    }

    @Test
    public void testEquals_equal_childClassAndContainedValue( ) {
        assertEquals( value, ChildOfValueTestHelper.create( containedValue ) );
    }

    @Test
    public void testEquals_notEqual_sameClassButDifferentContainedValue( ) {
        assertNotEquals( value, ValueTestHelper.create( "something else" ) );
    }

    @Test
    public void testEquals_notEqual_sameClassButNullValue( ) {
        assertNotEquals( value, valueContainingNull );
    }

    @Test
    public void testEquals_notEqual_differentClassButSameValue( ) {
        assertNotEquals( value, SiblingOfValueTestHelper.create( containedValue ) );
    }

    @Test
    public void testEquals_notEqual_differentClassAndDifferentValue( ) {
        assertNotEquals( value, SiblingOfValueTestHelper.create( "something else" ) );
    }

    @Test
    public void testEquals_notEqual_null( ) {
        assertNotEquals( value, null );
    }

    @Test
    public void testEquals_notEqual_containedValueIsNull( ) {
        assertNotEquals( value, valueContainingNull );
    }
}

class ValueTestHelper extends Value<Object>
{
    protected ValueTestHelper( Object obj ) {
        super( obj );
    }

    public static ValueTestHelper create( Object obj ) {
        return new ValueTestHelper( obj );
    }
}

class ChildOfValueTestHelper extends ValueTestHelper
{
    protected ChildOfValueTestHelper( Object obj ) {
        super( obj );
    }

    public static ChildOfValueTestHelper create( Object obj ) {
        return new ChildOfValueTestHelper( obj );
    }
}

class SiblingOfValueTestHelper extends Value<Object>
{
    protected SiblingOfValueTestHelper( Object obj ) {
        super( obj );
    }

    public static SiblingOfValueTestHelper create( Object obj ) {
        return new SiblingOfValueTestHelper( obj );
    }
}
