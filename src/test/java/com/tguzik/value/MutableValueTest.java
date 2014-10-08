package com.tguzik.value;

import static org.assertj.core.api.Assertions.assertThat;

import com.tguzik.tests.SettableHashCode;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class MutableValueTest {
    private MutableValue<?> valueContainingNull;
    private MutableValue<?> value;
    private String containedValue;

    @Before
    public void setUp() throws Exception {
        containedValue = "some value";

        valueContainingNull = ValueTestHelper.create( null );
        value = ValueTestHelper.create( containedValue );
    }

    @Test
    public void valueSetInConstructor() {
        MutableValue<Object> value = ValueTestHelper.create( containedValue );

        assertThat( value.get() ).isEqualTo( containedValue );
    }

    @Test
    public void setChangesTheValue() {
        MutableValue<Object> value = ValueTestHelper.create( containedValue );

        value.set( "new value" );

        assertThat( value.get() ).isEqualTo( "new value" );
    }

    @Test
    public void testGetValue() {
        assertThat( value.get() ).isSameAs( containedValue );
    }

    @Test
    public void testGetValue_null() {
        assertThat( valueContainingNull.get() ).isNull();
    }

    @Test
    public void testHashCode() {
        assertThat( ValueTestHelper.create( SettableHashCode.create( 123 ) ).hashCode() ).isEqualTo( 123 );
    }

    @Test
    public void testHashCode_null() {
        assertThat( valueContainingNull.hashCode() ).isZero();
    }

    @Test
    public void testToString() {
        assertThat( value.toString() ).isEqualTo( "some value" );
    }

    @Test
    public void testToString_null() {
        assertThat( valueContainingNull.toString() ).isEqualTo( "" );
    }

    @Test
    public void testEquals_equal_sameClassAndContainedValue() {
        assertThat( ValueTestHelper.create( containedValue ) ).isEqualTo( value );
    }

    @Test
    public void testEquals_equal_childClassAndContainedValue() {
        assertThat(value  ).isEqualTo( ChildOfValueTestHelper.create( containedValue ) );
    }

    @Test
    public void testEquals_notEqual_sameClassButDifferentContainedValue() {
        assertThat( ValueTestHelper.create( "something else" ) ).isNotEqualTo( value );
    }

    @Test
    public void testEquals_notEqual_sameClassButNullValue() {
        assertThat( valueContainingNull ).isNotEqualTo( value );
    }

    @Test
    public void testEquals_notEqual_differentClassButSameValue() {
        assertThat( SiblingOfValueTestHelper.create( containedValue ) ).isNotEqualTo( value );
    }

    @Test
    public void testEquals_notEqual_differentClassAndDifferentValue() {
        assertThat( SiblingOfValueTestHelper.create( "something else" ) ).isNotEqualTo( value );
    }

    @Test
    public void testEquals_notEqual_null() {
        assertThat( value ).isNotEqualTo( null );
    }

    @Test
    public void testEquals_notEqual_containedValueIsNull() {
        assertThat( valueContainingNull ).isNotEqualTo( value );
    }

    static class ValueTestHelper extends MutableValue<Object> {
        protected ValueTestHelper( Object obj ) {
            super( obj );
        }

        public static ValueTestHelper create( Object obj ) {
            return new ValueTestHelper( obj );
        }
    }

    static class ChildOfValueTestHelper extends ValueTestHelper {
        protected ChildOfValueTestHelper( Object obj ) {
            super( obj );
        }

        public static ChildOfValueTestHelper create( Object obj ) {
            return new ChildOfValueTestHelper( obj );
        }
    }

    static class SiblingOfValueTestHelper extends MutableValue<Object> {
        protected SiblingOfValueTestHelper( Object obj ) {
            super( obj );
        }

        public static SiblingOfValueTestHelper create( Object obj ) {
            return new SiblingOfValueTestHelper( obj );
        }
    }
}
