package com.tguzik.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.tguzik.value.Value;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings( { "deprecation" } )
public class SettableHashCodeTest {

    private SettableHashCode secondValue;
    private SettableHashCode value;

    @Before
    public void setUp() {
        this.secondValue = new SettableHashCode( 42 );
        this.value = SettableHashCode.create( 42 );
    }

    @Test
    public void equals_returns_false_on_different_objects() {
        assertThat( value ).isEqualTo( value ).isNotEqualTo( SettableHashCode.create( 321 ) );
    }

    @Test
    public void equals_is_reflexive() {
        assertThat( value ).isEqualTo( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
    }

    @Test
    public void equals_is_symmetric() {
        assertThat( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
        assertThat( secondValue ).isNotSameAs( value ).isEqualTo( value );
    }

    @Test
    public void equals_is_symmetric_doesnt_consider_sibling_classes_equal() {
        final Value<?> sibling = new Value<Integer>( 42 ) {
        };

        // Same contents
        assertThat( value.get() ).isEqualTo( sibling.get() );

        // But objects are not equal
        assertThat( value ).isNotEqualTo( sibling );
        assertThat( sibling ).isNotEqualTo( value );
    }

    @Test
    public void equals_is_transitive() {
        final Value<?> thirdValue = SettableHashCode.create( 42 );

        assertThat( value ).isNotSameAs( secondValue ).isNotSameAs( thirdValue ).isEqualTo( secondValue );
        assertThat( secondValue ).isNotSameAs( value ).isNotSameAs( thirdValue ).isEqualTo( thirdValue );
        assertThat( thirdValue ).isNotSameAs( value ).isNotSameAs( secondValue ).isEqualTo( value );
    }

    @Test
    public void equals_is_consistent() {
        assertThat( value ).isEqualTo( value ).isEqualTo( value ).isEqualTo( value );
    }

    @Test
    public void equals_returns_false_for_any_null_argument() {
        assertThat( value ).isNotEqualTo( null );
    }

    @Test
    public void hashCode_returns_hash_of_contained_value() {
        assertThat( SettableHashCode.create( 123 ).hashCode() ).isEqualTo( 123 );
    }

    @Test
    public void hashCode_returns_same_value_for_equal_object() {
        assertThat( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
        assertThat( value.hashCode() ).isEqualTo( value.hashCode() );
    }

}
