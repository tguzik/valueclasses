package com.tguzik.value;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.testing.EqualsTester;
import com.tguzik.tests.SettableHashCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MutableValueTest {
  private MutableValue<?> secondValueContainingNull;
  private MutableValue<?> valueContainingNull;
  private MutableValue<?> siblingOfValue;
  private MutableValue<?> childOfValue;
  private MutableValue<?> secondValue;
  private MutableValue<?> value;

  private String containedValue;

  @BeforeEach
  void setUp() {
    this.containedValue = "some value";

    this.secondValueContainingNull = new FakeMutableValue( null );
    this.valueContainingNull = new FakeMutableValue( null );
    this.secondValue = new FakeMutableValue( containedValue );
    this.value = new FakeMutableValue( containedValue );

    this.childOfValue = new ChildOfFakeMutableValue( containedValue );
    this.siblingOfValue = new SiblingOfFakeMutableValue( containedValue );
  }

  @Test
  void valueSetInConstructor() {
    final var value = new FakeMutableValue( containedValue );

    assertThat( value.get() ).isEqualTo( containedValue );
  }

  @Test
  void set_changes_internal_value() {
    final var localValue = new FakeMutableValue( containedValue );
    final String newString = "new value";

    assertThat( localValue.get() ).isEqualTo( containedValue );

    localValue.set( newString );

    assertThat( localValue.get() ).isEqualTo( newString );
  }

  @Test
  void get_returns_internal_value() {
    assertThat( value.get() ).isSameAs( containedValue );
  }

  @Test
  void get_returns_null_on_null_internal_value() {
    assertThat( valueContainingNull.get() ).isNull();
  }

  @Test
  void toString_returns_toString_of_internal_value() {
    assertThat( value.toString() ).isEqualTo( "some value" );
  }

  @Test
  void toString_returns_empty_string_on_null_internal_value() {
    assertThat( valueContainingNull.toString() ).isEqualTo( "" );
  }

  @Test
  void equals_returns_false_given_different_objects() {
    new EqualsTester().addEqualityGroup( value, secondValue )
                      .addEqualityGroup( valueContainingNull, secondValueContainingNull )
                      .testEquals();
  }

  @Test
  void equals_is_symmetric() {
    // Regular values
    assertThat( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
    assertThat( secondValue ).isNotSameAs( value ).isEqualTo( value );

    // Instances containing null
    assertThat( valueContainingNull ).isNotSameAs( secondValueContainingNull ).isEqualTo( secondValueContainingNull );
    assertThat( secondValueContainingNull ).isNotSameAs( valueContainingNull ).isEqualTo( valueContainingNull );
  }

  @Test
  void equals_is_symmetric_doesnt_consider_child_classes_equal() {
    final var childOfValueContainingNull = new ChildOfFakeMutableValue( null );

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
  void equals_doesnt_consider_sibling_classes_equal() {
    final var siblingOfValueContainingNull = new SiblingOfFakeMutableValue( null );

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
  void equals_returns_false_for_any_null_argument() {
    assertThat( value ).isNotEqualTo( null );
    assertThat( valueContainingNull ).isNotEqualTo( null );
  }

  @Test
  void hashCode_returns_hash_of_contained_value() {
    assertThat( new FakeMutableValue( new SettableHashCode( 123 ) ).hashCode() ).isEqualTo( 123 );
  }

  @Test
  void hashCode_returns_zero_if_contained_value_is_null() {
    assertThat( valueContainingNull.hashCode() ).isZero();
  }

  @Test
  void hashCode_returns_same_value_for_equal_object() {
    // Regular instances
    assertThat( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
    assertThat( value.hashCode() ).isEqualTo( value.hashCode() );

    // Just for smiles and giggles - values containing null
    assertThat( valueContainingNull ).isNotSameAs( secondValueContainingNull ).isEqualTo( secondValueContainingNull );
    assertThat( valueContainingNull.hashCode() ).isEqualTo( valueContainingNull.hashCode() );
  }

  @Test
  void hashCode_returns_different_value_for_different_object() {
    final Value<?> differentValue = new FakeMutableValue( "different value" );

    assertThat( value ).isNotEqualTo( differentValue );
    assertThat( value.hashCode() ).isNotEqualTo( differentValue.hashCode() );
  }

  static class FakeMutableValue extends MutableValue<Object> {
    public FakeMutableValue( Object obj ) {
      super( obj );
    }
  }

  static class ChildOfFakeMutableValue extends FakeMutableValue {
    public ChildOfFakeMutableValue( Object obj ) {
      super( obj );
    }
  }

  static class SiblingOfFakeMutableValue extends MutableValue<Object> {
    public SiblingOfFakeMutableValue( Object obj ) {
      super( obj );
    }
  }
}
