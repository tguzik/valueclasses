package com.tguzik.objects;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.testing.EqualsTester;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseObjectTest {
  private FakeObject secondValueContainingNull;
  private FakeObject differentInstanceField;
  private FakeObject valueContainingNull;
  private FakeObject secondValue;
  private FakeObject value;

  @BeforeEach
  void setUp() {
    secondValueContainingNull = new FakeObject();
    secondValueContainingNull.first = null;

    differentInstanceField = new FakeObject();
    differentInstanceField.third = "different instance field";

    valueContainingNull = new FakeObject();
    valueContainingNull.first = null;

    secondValue = new FakeObject();
    value = new FakeObject();
  }

  @AfterEach
  void tearDown() {
    // Return the static value to the state from before the test.
    FakeObject.PUBLIC_STATIC = "this is static";
  }

  @Test
  void static_toString_introspects_objects_via_reflection() {
    final Object object = new Object() {
      @SuppressWarnings( "unused" )
      final String value = "value contents";

      @SuppressWarnings( "unused" )
      final String secondValue = "different contents";
    };

    final String actual = BaseObject.toString( object, ToStringStyle.SIMPLE_STYLE );

    assertThat( actual ).isEqualTo( "different contents,value contents" );
  }

  @Test
  void static_toString_returns_empty_string_on_null_object() {
    assertThat( BaseObject.toString( null, ToStringStyle.SIMPLE_STYLE ) ).isEmpty();
  }

  @Test
  void toString_default_ToStringStyle_for_object_value() {
    final String expected = "BaseObjectTest.FakeObject[almostPI=3.14,first=first string,second=second string,third=third string]";

    final String actual = value.toString();

    assertThat( actual ).isEqualToNormalizingNewlines( expected );
  }

  @Test
  void toString_default_ToStringStyle_for_object_differentInstanceField() {
    final String expected =
      "BaseObjectTest.FakeObject[almostPI=3.14,first=first string,second=second string,third=different " + "instance field]";

    final String actual = differentInstanceField.toString();

    assertThat( actual ).isEqualTo( expected );
  }

  @Test
  void equals_returns_false_on_different_objects() {
    final var changedTransientField = new FakeObject();
    changedTransientField.transientField = "some other value";

    final var modifiedInstanceFieldOne = new FakeObject();
    modifiedInstanceFieldOne.third = "modified value";

    final var modifiedInstanceFieldTwo = new FakeObject();
    modifiedInstanceFieldTwo.third = "modified value";
    modifiedInstanceFieldTwo.transientField = "this value should be ignored because the field is transient";

    new EqualsTester().addEqualityGroup( new FakeObject(), new FakeObject(), changedTransientField )
                      .addEqualityGroup( modifiedInstanceFieldOne, modifiedInstanceFieldTwo )
                      .testEquals();
  }

  @Test
  void equals_does_not_consider_transient_fields() {
    final FakeObject other = new FakeObject();

    assertThat( value ).isEqualTo( other );
    assertThat( other ).isEqualTo( value );

    other.transientField = "different transient value";

    assertThat( value ).isEqualTo( other );
    assertThat( other ).isEqualTo( value );
  }

  @Test
  void equals_doesnt_consider_child_classes_equal() {
    final ChildOfFakeObject childOfValueContainingNull = new ChildOfFakeObject();
    final ChildOfFakeObject childOfValue = new ChildOfFakeObject();

    childOfValueContainingNull.first = null;

    assertThat( value ).isNotEqualTo( childOfValue );
    assertThat( childOfValue ).isNotEqualTo( value );

    assertThat( valueContainingNull ).isNotEqualTo( childOfValueContainingNull );
    assertThat( childOfValueContainingNull ).isNotEqualTo( valueContainingNull );
  }

  @Test
  void equals_doesnt_consider_sibling_classes_equal() {
    final SiblingOfFakeObject siblingOfValueContainingNull = new SiblingOfFakeObject();
    final SiblingOfFakeObject siblingOfValue = new SiblingOfFakeObject();

    siblingOfValueContainingNull.first = null;

    assertThat( value ).isNotEqualTo( siblingOfValue );
    assertThat( siblingOfValue ).isNotEqualTo( value );

    assertThat( valueContainingNull ).isNotEqualTo( siblingOfValueContainingNull );
    assertThat( siblingOfValueContainingNull ).isNotEqualTo( valueContainingNull );
  }

  @Test
  void equals_is_transitive() {
    final FakeObject thirdValueContainingNull = new FakeObject();
    final FakeObject thirdValue = new FakeObject();

    thirdValueContainingNull.first = null;

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
    assertThat( value ).isNotEqualTo( differentInstanceField );
    assertThat( value.hashCode() ).isNotEqualTo( differentInstanceField.hashCode() );
  }

  @Test
  void hashCode_is_repeatable() {
    assertThat( value.hashCode() ).isEqualTo( value.hashCode() );
    assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );
  }

  @Test
  void hashCode_does_not_consider_static_fields() {
    assertThat( value.hashCode() ).isEqualTo( value.hashCode() );
    assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );

    FakeObject.PUBLIC_STATIC = "different static value";

    assertThat( value.hashCode() ).isEqualTo( value.hashCode() );
    assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );
  }

  @Test
  void hashCode_does_not_consider_transient_fields() {
    final FakeObject other = new FakeObject();

    assertThat( value ).isEqualTo( other );
    assertThat( other ).isEqualTo( value );
    assertThat( value.hashCode() ).isEqualTo( other.hashCode() );

    other.transientField = "different transient value";

    assertThat( value ).isEqualTo( other );
    assertThat( other ).isEqualTo( value );
    assertThat( value.hashCode() ).isEqualTo( other.hashCode() );
  }

  @SuppressWarnings( "unused" )
  static class FakeObject extends BaseObject {
    private static final String PRIVATE_STATIC = "private static string";
    public static String PUBLIC_STATIC = "public static string";

    protected volatile String first = "first string";
    final String second = "second string";
    public String third = "third string";
    double almostPI = 3.14;
    public transient String transientField = "transient ";
  }

  static class ChildOfFakeObject extends FakeObject {

  }

  @SuppressWarnings( "unused" )
  static class SiblingOfFakeObject extends BaseObject {
    private static final String PRIVATE_STATIC = "private static string";
    public static String PUBLIC_STATIC = "public static string";

    protected volatile String first = "first string";
    final String second = "second string";
    public String third = "third string";
    double almostPI = 3.14;
    public transient String transientField = "transient ";
  }
}
