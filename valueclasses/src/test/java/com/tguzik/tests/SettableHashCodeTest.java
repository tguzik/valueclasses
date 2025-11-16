package com.tguzik.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.testing.EqualsTester;
import com.tguzik.value.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SettableHashCodeTest {

  @ParameterizedTest
  @ValueSource( ints = { Integer.MIN_VALUE, -42, -3, -2, -1, 0, 1, 2, 3, 42, Integer.MAX_VALUE } )
  void hashCode_returns_hash_of_contained_value( final int value ) {
    final var instance = new SettableHashCode( value );

    assertThat( instance.hashCode() ).isEqualTo( value );
  }

  @Test
  void equals_returns_false_on_different_objects() {
    new EqualsTester().addEqualityGroup( new SettableHashCode( 42 ), new SettableHashCode( 42 ) )
                      .addEqualityGroup( new SettableHashCode( 123 ), new SettableHashCode( 123 ) )
                      .testEquals();
  }

  @Test
  void equals_is_symmetric_doesnt_consider_sibling_classes_equal() {
    final var value = new SettableHashCode( 42 );
    final Value<?> sibling = new Value<>( 42 ) {
    };

    // Same contents
    assertThat( value.get() ).isEqualTo( sibling.get() );

    // But objects are not equal
    assertThat( value ).isNotEqualTo( sibling );
    assertThat( sibling ).isNotEqualTo( value );
  }

  @Test
  void equals_is_transitive() {
    final var value = new SettableHashCode( 42 );
    final var secondValue = new SettableHashCode( 42 );
    final Value<?> thirdValue = new SettableHashCode( 42 );

    assertThat( value ).isNotSameAs( secondValue ).isNotSameAs( thirdValue ).isEqualTo( secondValue );
    assertThat( secondValue ).isNotSameAs( value ).isNotSameAs( thirdValue ).isEqualTo( thirdValue );
    assertThat( thirdValue ).isNotSameAs( value ).isNotSameAs( secondValue ).isEqualTo( value );
  }
}
