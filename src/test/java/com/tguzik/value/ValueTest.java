package com.tguzik.value;

import static org.assertj.core.api.Assertions.assertThat;

import com.tguzik.tests.SettableHashCode;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 */
public class ValueTest {
    private SiblingOfValueTestHelper siblingOfValue;
    private ChildOfValueTestHelper childOfValue;

    private Value<?> secondValueContainingNull;
    private Value<?> valueContainingNull;
    private Value<?> secondValue;
    private Value<?> value;

    private String containedValue;

    @Before
    public void setUp() {
        this.containedValue = "some value";

        this.secondValueContainingNull = ValueTestHelper.create( null );
        this.valueContainingNull = ValueTestHelper.create( null );

        this.value = ValueTestHelper.create( containedValue );
        this.secondValue = ValueTestHelper.create( containedValue );

        this.childOfValue = ChildOfValueTestHelper.create( containedValue );
        this.siblingOfValue = SiblingOfValueTestHelper.create( containedValue );
    }

    @Test
    public void get_returns_internal_value() {
        assertThat( value.get() ).isSameAs( containedValue );
    }

    @Test
    public void get_returns_null_on_null_internal_value() {
        assertThat( valueContainingNull.get() ).isNull();
    }

    @Test
    public void toString_returns_toString_of_internal_value() {
        assertThat( value.toString() ).isEqualTo( "some value" );
    }

    @Test
    public void toString_returns_empty_string_on_null_internal_value() {
        assertThat( valueContainingNull.toString() ).isEqualTo( "" );
    }

    @Test
    public void equals_returns_false_on_different_objects() {
        assertThat( value ).isEqualTo( value ).isNotEqualTo( ValueTestHelper.create( "some other value" ) );
    }

    @Test
    public void equals_is_reflexive() {
        assertThat( value ).isEqualTo( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
        assertThat( valueContainingNull ).isEqualTo( valueContainingNull )
                                         .isNotSameAs( secondValueContainingNull )
                                         .isEqualTo( secondValueContainingNull );
    }

    @Test
    public void equals_is_symmetric() {
        // Regular values
        assertThat( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
        assertThat( secondValue ).isNotSameAs( value ).isEqualTo( value );

        // Instances containing null
        assertThat( valueContainingNull ).isNotSameAs( secondValueContainingNull )
                                         .isEqualTo( secondValueContainingNull );
        assertThat( secondValueContainingNull ).isNotSameAs( valueContainingNull ).isEqualTo( valueContainingNull );
    }

    @Test
    public void equals_is_symmetric_doesnt_consider_child_classes_equal() {
        final Value<?> childOfValueContainingNull = ChildOfValueTestHelper.create( null );

        // Same contents
        assertThat( value.get() ).isEqualTo( childOfValue.get() );
        assertThat( valueContainingNull.get() ).isEqualTo( childOfValueContainingNull.get() );

        // But objects are not equal
        assertThat( value ).isNotEqualTo( childOfValue );
        assertThat( childOfValue ).isNotEqualTo( value );

        assertThat( valueContainingNull ).isNotEqualTo( childOfValueContainingNull );
        assertThat( childOfValueContainingNull ).isNotEqualTo( valueContainingNull );
    }

    @Test
    public void equals_is_symmetric_doesnt_consider_sibling_classes_equal() {
        final Value<?> siblingOfValueContainingNull = SiblingOfValueTestHelper.create( null );

        // Same contents
        assertThat( value.get() ).isEqualTo( siblingOfValue.get() );
        assertThat( valueContainingNull.get() ).isEqualTo( siblingOfValueContainingNull.get() );

        // But objects are not equal
        assertThat( value ).isNotEqualTo( siblingOfValue );
        assertThat( siblingOfValue ).isNotEqualTo( value );

        assertThat( valueContainingNull ).isNotEqualTo( siblingOfValueContainingNull );
        assertThat( siblingOfValueContainingNull ).isNotEqualTo( valueContainingNull );
    }

    @Test
    public void equals_is_transitive() {
        final Value<?> thirdValueContainingNull = ValueTestHelper.create( null );
        final Value<?> thirdValue = ValueTestHelper.create( containedValue );

        // Regular values
        assertThat( value ).isNotSameAs( secondValue ).isNotSameAs( thirdValue ).isEqualTo( secondValue );
        assertThat( secondValue ).isNotSameAs( value ).isNotSameAs( thirdValue ).isEqualTo( thirdValue );
        assertThat( thirdValue ).isNotSameAs( value ).isNotSameAs( secondValue ).isEqualTo( value );

        // Instances containing null
        assertThat( valueContainingNull ).isNotSameAs( secondValueContainingNull )
                                         .isNotSameAs( thirdValueContainingNull )
                                         .isEqualTo( secondValueContainingNull );
        assertThat( secondValueContainingNull ).isNotSameAs( valueContainingNull )
                                               .isNotSameAs( thirdValueContainingNull )
                                               .isEqualTo( thirdValueContainingNull );
        assertThat( thirdValueContainingNull ).isNotSameAs( valueContainingNull )
                                              .isNotSameAs( secondValueContainingNull )
                                              .isEqualTo( valueContainingNull );
    }

    @Test
    public void equals_is_consistent() {
        // Regular values
        assertThat( value ).isEqualTo( value ).isEqualTo( value ).isEqualTo( value );

        // Instances containing null
        assertThat( valueContainingNull ).isEqualTo( valueContainingNull )
                                         .isEqualTo( valueContainingNull )
                                         .isEqualTo( valueContainingNull );
    }

    @Test
    public void equals_returns_false_for_any_null_argument() {
        assertThat( value ).isNotEqualTo( null );
        assertThat( valueContainingNull ).isNotEqualTo( null );
    }

    @Test
    public void hashCode_returns_hash_of_contained_value() {
        assertThat( ValueTestHelper.create( new SettableHashCode( 123 ) ).hashCode() ).isEqualTo( 123 );
    }

    @Test
    public void hashCode_returns_zero_if_contained_value_is_null() {
        assertThat( valueContainingNull.hashCode() ).isZero();
    }

    @Test
    public void hashCode_returns_same_value_for_equal_object() {
        // Regular instances
        assertThat( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
        assertThat( value.hashCode() ).isEqualTo( value.hashCode() );

        // Just for smiles and giggles - values containing null
        assertThat( valueContainingNull ).isNotSameAs( secondValueContainingNull )
                                         .isEqualTo( secondValueContainingNull );
        assertThat( valueContainingNull.hashCode() ).isEqualTo( valueContainingNull.hashCode() );
    }

    @Test
    public void hashCode_returns_different_value_for_different_object() {
        final Value<?> differentValue = ValueTestHelper.create( "different value" );

        assertThat( value ).isNotEqualTo( differentValue );
        assertThat( value.hashCode() ).isNotEqualTo( differentValue.hashCode() );
    }

    static class ValueTestHelper extends Value<Object> {
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

    static class SiblingOfValueTestHelper extends Value<Object> {
        protected SiblingOfValueTestHelper( Object obj ) {
            super( obj );
        }

        public static SiblingOfValueTestHelper create( Object obj ) {
            return new SiblingOfValueTestHelper( obj );
        }
    }
}
