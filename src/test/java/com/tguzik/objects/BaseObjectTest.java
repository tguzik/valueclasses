package com.tguzik.objects;

import static com.tguzik.unittests.Loader.loadFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tguzik.objects.BaseObject;
import com.tguzik.unittests.Normalize;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class BaseObjectTest
{
    private BaseObjectTestHelper differentInstanceField;
    private BaseObjectTestHelper base;

    @Before
    public void setUp( ) throws Exception {
        differentInstanceField = new BaseObjectTestHelper();
        base = new BaseObjectTestHelper();

        differentInstanceField.third = "different instance field";
    }

    @After
    public void tearDown( ) {
        BaseObjectTestHelper.publicStatic = "this is static";
    }

    @Test
    public void testEquals( ) {
        assertEquals( base, base );
        assertNotEquals( base, differentInstanceField );
    }

    @Test
    public void testEquals_staticFieldsNotChecked( ) {
        assertEquals( base, base );
        assertEquals( differentInstanceField, differentInstanceField );

        BaseObjectTestHelper.publicStatic = "different static value";

        assertEquals( base, base );
        assertEquals( differentInstanceField, differentInstanceField );
    }

    @Test
    public void testToString_default_base( ) throws IOException {
        String expected = loadFile( "test", getClass(), "data", "tostring-default-base.txt" );
        String actual = base.toString();

        assertEquals( expected, Normalize.newLines( actual ) );
    }

    @Test
    public void testToString_default_differentInstanceField( ) throws IOException {
        String expected = loadFile( "test", getClass(), "data", "tostring-default-value.txt" );
        String actual = differentInstanceField.toString();

        assertEquals( expected, Normalize.newLines( actual ) );
    }

    @Test
    public void testToString_customToStringStyle_base( ) throws IOException {
        String expected = loadFile( "test", getClass(), "data", "tostring-customToStringStyle-base.txt" );
        String actual = base.toString( BaseObject.MULTILINE_NO_ADDRESS_TOSTRING_STYLE );

        assertEquals( expected, Normalize.newLines( actual ) );
    }

    @Test
    public void testToString_customToStringStyle_differentInstanceField( ) throws IOException {
        String expected = loadFile( "test", getClass(), "data", "tostring-customToStringStyle-value.txt" );
        String actual = differentInstanceField.toString( BaseObject.MULTILINE_NO_ADDRESS_TOSTRING_STYLE );

        assertEquals( expected, Normalize.newLines( actual ) );
    }

    @Test
    public void testHashCode( ) {
        assertEquals( base.hashCode(), base.hashCode() );
        assertEquals( differentInstanceField.hashCode(), differentInstanceField.hashCode() );
        assertNotEquals( base.hashCode(), differentInstanceField.hashCode() );
    }

    @Test
    public void testHashCode_doesNotConsiderStaticFields( ) {
        assertEquals( base.hashCode(), base.hashCode() );
        assertEquals( differentInstanceField.hashCode(), differentInstanceField.hashCode() );

        BaseObjectTestHelper.publicStatic = "different static value";

        assertEquals( base.hashCode(), base.hashCode() );
        assertEquals( differentInstanceField.hashCode(), differentInstanceField.hashCode() );
    }

    static class BaseObjectTestHelper extends BaseObject
    {
        @SuppressWarnings( "unused" )
        private volatile String first = "first string";

        @SuppressWarnings( "unused" )
        private static String staticString = "this is static";

        public static String publicStatic = "this is static";

        final String second = "second string";
        public String third = "third string";
        double almostPI = 3.14;
    }
}
