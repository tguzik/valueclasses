package com.tguzik.objects;

import static com.tguzik.tests.Loader.loadFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import com.tguzik.tests.Normalize;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class BaseObjectTest {
    private BaseObjectTestHelper secondValueContainingNull;
    private BaseObjectTestHelper differentInstanceField;
    private BaseObjectTestHelper valueContainingNull;
    private BaseObjectTestHelper secondValue;
    private BaseObjectTestHelper value;

    @Before
    public void setUp() throws Exception {
        secondValueContainingNull = new BaseObjectTestHelper();
        secondValueContainingNull.first = null;

        differentInstanceField = new BaseObjectTestHelper();
        differentInstanceField.third = "different instance field";

        valueContainingNull = new BaseObjectTestHelper();
        valueContainingNull.first = null;

        secondValue = new BaseObjectTestHelper();
        value = new BaseObjectTestHelper();
    }

    @After
    public void tearDown() {
        // Return the static value to the state from before the test.
        BaseObjectTestHelper.publicStatic = "this is static";
    }

    @Test
    public void static_toString_introspects_objects_via_reflection() throws IOException {
        Object object = new Object() {
            private String value = "value contents";
            private String secondValue = "different contents";
        };
        final String expected = "value contents,different contents";
        final String actual = BaseObject.toString( object, ToStringStyle.SIMPLE_STYLE );

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    public void static_toString_returns_empty_string_on_null_object() throws IOException {
        assertThat( BaseObject.toString( null, ToStringStyle.SIMPLE_STYLE ) ).isEmpty();
    }

    @Test( expected = Exception.class )
    public void static_toString_throws_exception_on_null_toStringStyle() throws IOException {
        BaseObject.toString( this, null );
    }

    @Test( expected = Exception.class )
    public void static_toString_exception_on_null_toStringStyle_has_priority_over_null_object() throws IOException {
        BaseObject.toString( null, null );
    }

    @Test
    public void toString_default_ToStringStyle_for_object_value() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-default-value.txt" );
        String actual = value.toString();

        assertThat( expected ).isEqualTo( Normalize.newLines( actual ) );
    }

    @Test
    public void toString_default_ToStringStyle_for_object_differentInstanceField() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-default-differentInstanceField.txt" );
        String actual = differentInstanceField.toString();

        assertThat( expected ).isEqualTo( Normalize.newLines( actual ) );
    }

    @Test
    public void toString_custom_ToStringStyle_for_object_value() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-customToStringStyle-value.txt" );
        String actual = value.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE );

        assertThat( expected ).isEqualTo( Normalize.newLines( actual ) );
    }

    @Test
    public void toString_custom_ToStringStyle_for_object_differentInstanceField() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-customToStringStyle-differentInstanceField.txt" );
        String actual = differentInstanceField.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE );

        assertThat( expected ).isEqualTo( Normalize.newLines( actual ) );
    }

    @Test
    public void equals_returns_false_on_different_objects() {
        assertThat( value ).isEqualTo( value ).isNotEqualTo( differentInstanceField );
    }

    @Test
    public void equals_does_not_consider_static_fields() {
        assertThat( value ).isEqualTo( value );
        assertThat( differentInstanceField ).isEqualTo( differentInstanceField );

        BaseObjectTestHelper.publicStatic = "different static value";

        assertThat( value ).isEqualTo( value );
        assertThat( differentInstanceField ).isEqualTo( differentInstanceField );
    }

    @Test
    public void equals_does_not_consider_transient_fields() {
        final BaseObjectTestHelper other = new BaseObjectTestHelper();

        assertThat( value ).isEqualTo( other );
        assertThat( other ).isEqualTo( value );

        other.transientField = "different transient value";

        assertThat( value ).isEqualTo( other );
        assertThat( other ).isEqualTo( value );
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
        final ChildOfBaseObjectTestHelper childOfValueContainingNull = new ChildOfBaseObjectTestHelper();
        final ChildOfBaseObjectTestHelper childOfValue = new ChildOfBaseObjectTestHelper();

        childOfValueContainingNull.first = null;

        assertThat( value ).isNotEqualTo( childOfValue );
        assertThat( childOfValue ).isNotEqualTo( value );

        assertThat( valueContainingNull ).isNotEqualTo( childOfValueContainingNull );
        assertThat( childOfValueContainingNull ).isNotEqualTo( valueContainingNull );
    }

    @Test
    public void equals_is_symmetric_doesnt_consider_sibling_classes_equal() {
        final SiblingOfBaseObjectTestHelper siblingOfValueContainingNull = new SiblingOfBaseObjectTestHelper();
        final SiblingOfBaseObjectTestHelper siblingOfValue = new SiblingOfBaseObjectTestHelper();

        siblingOfValueContainingNull.first = null;

        assertThat( value ).isNotEqualTo( siblingOfValue );
        assertThat( siblingOfValue ).isNotEqualTo( value );

        assertThat( valueContainingNull ).isNotEqualTo( siblingOfValueContainingNull );
        assertThat( siblingOfValueContainingNull ).isNotEqualTo( valueContainingNull );
    }

    @Test
    public void equals_is_transitive() {
        final BaseObjectTestHelper thirdValueContainingNull = new BaseObjectTestHelper();
        final BaseObjectTestHelper thirdValue = new BaseObjectTestHelper();

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
        assertThat( value ).isNotEqualTo( differentInstanceField );
        assertThat( value.hashCode() ).isNotEqualTo( differentInstanceField.hashCode() );
    }

    @Test
    public void hashCode_is_repeatable() {
        assertThat( value.hashCode() ).isEqualTo( value.hashCode() );
        assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );
    }

    @Test
    public void hashCode_does_not_consider_static_fields() {
        assertThat( value.hashCode() ).isEqualTo( value.hashCode() );
        assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );

        BaseObjectTestHelper.publicStatic = "different static value";

        assertThat( value.hashCode() ).isEqualTo( value.hashCode() );
        assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );
    }

    @Test
    public void hashCode_does_not_consider_transient_fields() {
        final BaseObjectTestHelper other = new BaseObjectTestHelper();

        assertThat( value ).isEqualTo( other );
        assertThat( other ).isEqualTo( value );
        assertThat( value.hashCode() ).isEqualTo( other.hashCode() );

        other.transientField = "different transient value";

        assertThat( value ).isEqualTo( other );
        assertThat( other ).isEqualTo( value );
        assertThat( value.hashCode() ).isEqualTo( other.hashCode() );
    }

    static class BaseObjectTestHelper extends BaseObject {
        @SuppressWarnings( "unused" )
        protected volatile String first = "first string";

        @SuppressWarnings( "unused" )
        private static String staticString = "this is static";

        public static String publicStatic = "this is static";

        final String second = "second string";
        public String third = "third string";
        double almostPI = 3.14;

        public transient String transientField = "transient ";
    }

    static class ChildOfBaseObjectTestHelper extends BaseObjectTestHelper {

    }

    static class SiblingOfBaseObjectTestHelper extends BaseObject {
        @SuppressWarnings( "unused" )
        protected volatile String first = "first string";

        @SuppressWarnings( "unused" )
        private static String staticString = "this is static";

        public static String publicStatic = "this is static";

        final String second = "second string";
        public String third = "third string";
        double almostPI = 3.14;

        public transient String transientField = "transient ";
    }
}
