package com.tguzik.value;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.testing.EqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValueTest {
  private SiblingOfFakeValue siblingOfValue;
  private ChildOfFakeValue childOfValue;

  private Value<?> secondValueContainingNull;
  private Value<?> valueContainingNull;
  private Value<?> secondValue;
  private Value<?> value;

  private String containedValue;

  @BeforeEach
  void setUp() {
    this.containedValue = "some value";

    this.secondValueContainingNull = new FakeValue( null );
    this.valueContainingNull = new FakeValue( null );

    this.value = new FakeValue( containedValue );
    this.secondValue = new FakeValue( containedValue );

    this.childOfValue = new ChildOfFakeValue( containedValue );
    this.siblingOfValue = new SiblingOfFakeValue( containedValue );
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
  void equals_returns_false_on_different_objects() {
    new EqualsTester().addEqualityGroup( new FakeValue( "some value" ), new FakeValue( "some value" ) )
                      .addEqualityGroup( new FakeValue( null ), new FakeValue( null ) )
                      .addEqualityGroup( new ChildOfFakeValue( "val" ), new ChildOfFakeValue( "val" ) )
                      .addEqualityGroup( new SiblingOfFakeValue( "val" ), new SiblingOfFakeValue( "val" ) )
                      .testEquals();
  }

  @Test
  void equals_doesnt_consider_child_classes_equal() {
    final Value<?> childOfValueContainingNull = new ChildOfFakeValue( null );

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
    final Value<?> siblingOfValueContainingNull = new SiblingOfFakeValue( null );

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
  void hashCode_returns_hash_of_contained_value() {
    final var predefinedHashCode = new Object() {
      @Override
      public int hashCode() {
        return 123;
      }
    };

    assertThat( new FakeValue( predefinedHashCode ).hashCode() ).isEqualTo( 123 );
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
    final Value<?> differentValue = new FakeValue( "different value" );

    assertThat( value ).isNotEqualTo( differentValue );
    assertThat( value.hashCode() ).isNotEqualTo( differentValue.hashCode() );
  }

  static class FakeValue extends Value<Object> {
    public FakeValue( Object obj ) {
      super( obj );
    }
  }

  static class ChildOfFakeValue extends FakeValue {
    public ChildOfFakeValue( Object obj ) {
      super( obj );
    }
  }

  static class SiblingOfFakeValue extends Value<Object> {
    public SiblingOfFakeValue( Object obj ) {
      super( obj );
    }
  }
}
