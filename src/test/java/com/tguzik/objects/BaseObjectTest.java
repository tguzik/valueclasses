package com.tguzik.objects;

import static com.tguzik.tests.Loader.loadFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import com.tguzik.annotations.RefactorThis;
import com.tguzik.tests.Normalize;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@RefactorThis( "Needs a full equals/hashCode contract check" )
public class BaseObjectTest {
    private BaseObjectTestHelper differentInstanceField;
    private BaseObjectTestHelper base;

    @Before
    public void setUp() throws Exception {
        differentInstanceField = new BaseObjectTestHelper();
        base = new BaseObjectTestHelper();

        differentInstanceField.third = "different instance field";
    }

    @After
    public void tearDown() {
        BaseObjectTestHelper.publicStatic = "this is static";
    }

    @Test
    public void testEquals() {
        assertThat( base ).isEqualTo( base ).isNotEqualTo( differentInstanceField );
    }

    @Test
    public void testEquals_staticFieldsNotChecked() {
        assertThat( base ).isEqualTo( base );
        assertThat( differentInstanceField ).isEqualTo( differentInstanceField );

        BaseObjectTestHelper.publicStatic = "different static value";

        assertThat( base ).isEqualTo( base );
        assertThat( differentInstanceField ).isEqualTo( differentInstanceField );
    }

    @Test
    public void testEquals_transientFieldsNotChecked() {
        final BaseObjectTestHelper other = new BaseObjectTestHelper();

        assertThat( base ).isEqualTo( other );
        assertThat( other ).isEqualTo( base );

        other.transientField = "different transient value";

        assertThat( base ).isEqualTo( other );
        assertThat( other ).isEqualTo( base );
    }

    @Test
    public void testToString_default_base() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-default-base.txt" );
        String actual = base.toString();

        assertThat( Normalize.newLines( actual ) ).isEqualTo( expected );
    }

    @Test
    public void testToString_default_differentInstanceField() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-default-value.txt" );
        String actual = differentInstanceField.toString();

        assertThat( Normalize.newLines( actual ) ).isEqualTo( expected );
    }

    @Test
    public void testToString_customToStringStyle_base() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-customToStringStyle-base.txt" );
        String actual = base.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE );

        assertThat( Normalize.newLines( actual ) ).isEqualTo( expected );
    }

    @Test
    public void testToString_customToStringStyle_differentInstanceField() throws IOException {
        String expected = loadFile( getClass(), "data", "tostring-customToStringStyle-value.txt" );
        String actual = differentInstanceField.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE );

        assertThat( Normalize.newLines( actual ) ).isEqualTo( expected );
    }

    @Test
    public void testHashCode() {
        assertThat( base.hashCode() ).isNotEqualTo( differentInstanceField.hashCode() );
        assertThat( differentInstanceField.hashCode() ).isNotEqualTo( base.hashCode() );
    }

    @Test
    public void testHashCode_isRepeatable() {
        assertThat( base.hashCode() ).isEqualTo( base.hashCode() );
        assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );
    }

    @Test
    public void testHashCode_doesNotConsiderStaticFields() {
        assertThat( base.hashCode() ).isEqualTo( base.hashCode() );
        assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );

        BaseObjectTestHelper.publicStatic = "different static value";

        assertThat( base.hashCode() ).isEqualTo( base.hashCode() );
        assertThat( differentInstanceField.hashCode() ).isEqualTo( differentInstanceField.hashCode() );
    }

    @Test
    public void testHashCode_doesNotConsiderTransientFields() {
        final BaseObjectTestHelper other = new BaseObjectTestHelper();

        assertThat( base ).isEqualTo( other );
        assertThat( other ).isEqualTo( base );
        assertThat( base.hashCode() ).isEqualTo( other.hashCode() );

        other.transientField = "different transient value";

        assertThat( base ).isEqualTo( other );
        assertThat( other ).isEqualTo( base );
        assertThat( base.hashCode() ).isEqualTo( other.hashCode() );
    }

    static class BaseObjectTestHelper extends BaseObject {
        @SuppressWarnings( "unused" )
        private volatile String first = "first string";

        @SuppressWarnings( "unused" )
        private static String staticString = "this is static";

        public static String publicStatic = "this is static";

        final String second = "second string";
        public String third = "third string";
        double almostPI = 3.14;

        public transient String transientField = "transient ";
    }
}
