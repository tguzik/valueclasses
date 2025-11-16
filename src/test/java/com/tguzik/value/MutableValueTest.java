package com.tguzik.value;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.testing.EqualsTester;
import com.tguzik.tests.SettableHashCode;
import org.junit.Before;
import org.junit.Test;

public class MutableValueTest {
  private MutableValue<?> secondValueContainingNull;
  private MutableValue<?> valueContainingNull;
  private MutableValue<?> siblingOfValue;
  private MutableValue<?> childOfValue;
  private MutableValue<?> secondValue;
  private MutableValue<?> value;

  private String containedValue;

  @Before
  public void setUp() {
    this.containedValue = "some value";

    this.secondValueContainingNull = new ValueTestHelper( null );
    this.valueContainingNull = new ValueTestHelper( null );
    this.secondValue = new ValueTestHelper( containedValue );
    this.value = new ValueTestHelper( containedValue );

    this.childOfValue = new ChildOfValueTestHelper( containedValue );
    this.siblingOfValue = new SiblingOfValueTestHelper( containedValue );
  }

  @Test
  public void valueSetInConstructor() {
    MutableValue<Object> value = new ValueTestHelper( containedValue );

    assertThat( value.get() ).isEqualTo( containedValue );
  }

  @Test
  public void set_changes_internal_value() {
    final MutableValue<Object> localValue = new ValueTestHelper( containedValue );
    final String newString = "new value";

    assertThat( localValue.get() ).isEqualTo( containedValue );

    localValue.set( newString );

    assertThat( localValue.get() ).isEqualTo( newString );
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
  public void equals_returns_false_given_different_objects() {
    new EqualsTester().addEqualityGroup( value, secondValue )
                      .addEqualityGroup( valueContainingNull, secondValueContainingNull )
                      .testEquals();
  }

  @Test
  public void equals_is_symmetric() {
    // Regular values
    assertThat( value ).isNotSameAs( secondValue ).isEqualTo( secondValue );
    assertThat( secondValue ).isNotSameAs( value ).isEqualTo( value );

    // Instances containing null
    assertThat( valueContainingNull ).isNotSameAs( secondValueContainingNull ).isEqualTo( secondValueContainingNull );
    assertThat( secondValueContainingNull ).isNotSameAs( valueContainingNull ).isEqualTo( valueContainingNull );
  }

  @Test
  public void equals_is_symmetric_doesnt_consider_child_classes_equal() {
    final Value<?> childOfValueContainingNull = new ChildOfValueTestHelper( null );

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
  public void equals_idoesnt_consider_sibling_classes_equal() {
    final Value<?> siblingOfValueContainingNull = new SiblingOfValueTestHelper( null );

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
  public void equals_returns_false_for_any_null_argument() {
    assertThat( value ).isNotEqualTo( null );
    assertThat( valueContainingNull ).isNotEqualTo( null );
  }

  @Test
  public void hashCode_returns_hash_of_contained_value() {
    assertThat( new ValueTestHelper( new SettableHashCode( 123 ) ).hashCode() ).isEqualTo( 123 );
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
    assertThat( valueContainingNull ).isNotSameAs( secondValueContainingNull ).isEqualTo( secondValueContainingNull );
    assertThat( valueContainingNull.hashCode() ).isEqualTo( valueContainingNull.hashCode() );
  }

  @Test
  public void hashCode_returns_different_value_for_different_object() {
    final Value<?> differentValue = new ValueTestHelper( "different value" );

    assertThat( value ).isNotEqualTo( differentValue );
    assertThat( value.hashCode() ).isNotEqualTo( differentValue.hashCode() );
  }

  static class ValueTestHelper extends MutableValue<Object> {
    public ValueTestHelper( Object obj ) {
      super( obj );
    }
  }

  static class ChildOfValueTestHelper extends ValueTestHelper {
    public ChildOfValueTestHelper( Object obj ) {
      super( obj );
    }
  }

  static class SiblingOfValueTestHelper extends MutableValue<Object> {
    public SiblingOfValueTestHelper( Object obj ) {
      super( obj );
    }
  }
}
