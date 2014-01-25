package com.tguzik.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class OverridingImmutableTableBuilderTest
{
    private OverridingImmutableTableBuilder<String, String, String> builder_noInitial;
    private OverridingImmutableTableBuilder<String, String, String> builder_withInitial;

    private Table<String, String, String> empty;
    private Table<String, String, String> first;
    private Table<String, String, String> second;
    private Table<String, String, String> third;
    private Table<String, String, String> expected;

    @Before
    public void setUp( ) throws Exception {
        empty = ImmutableTable.of();
        first = ImmutableTable.<String, String, String> builder()
                              .put( "row1", "col1", "value in first" )
                              .put( "row1", "col2", "another value in first" )
                              .build();

        second = ImmutableTable.<String, String, String> builder()
                               .put( "row1", "col1", "value in second" )
                               .put( "", "col3", "value in second" )
                               .build();

        third = ImmutableTable.<String, String, String> builder()
                              .put( "", "col3", "value in third" )
                              .put( "row2", "col2", "value in third" )
                              .build();

        expected = ImmutableTable.<String, String, String> builder()
                                 .put( "", "col3", "value in third" )
                                 .put( "row1", "col1", "value in second" )
                                 .put( "row1", "col2", "another value in first" )
                                 .put( "row2", "col2", "value in third" )
                                 .build();

        builder_noInitial = OverridingImmutableTableBuilder.create();
        builder_withInitial = OverridingImmutableTableBuilder.create( first );
    }

    @Test
    public void testCreate_withInitialTable( ) {
        assertEquals( "{row1={col1=value in first, col2=another value in first}}",
                      builder_withInitial.build().toString() );
    }

    @Test
    public void testCreate_withoutInitialTable( ) {
        Table<?, ?, ?> actual = builder_noInitial.build();

        assertNotNull( actual );
        assertEquals( empty, actual );
        assertTrue( actual.isEmpty() );
        assertEquals( 0, actual.size() );

        assertEquals( "{}", actual.toString() );
    }

    @Test
    public void testOverrideWith_withoutInitialTable( ) {
        Table<?, ?, ?> actual = builder_noInitial.overrideWith( first )
                                                 .overrideWith( second )
                                                 .overrideWith( third )
                                                 .build();

        assertEquals( 4, actual.size() );
        assertEquals( expected, actual );
        assertEquals( expected.toString(), actual.toString() );
        assertEquals( "{={col3=value in third}, row1={col1=value in second, col2=another value in first}, row2={col2=value in third}}",
                      actual.toString() );
    }

    @Test
    public void testOverrideWith_explicitTwoArguments( ) {
        Table<?, ?, ?> actual = builder_noInitial.overrideWith( "row", "col", "value" ).build();

        assertEquals( 1, actual.size() );
        assertEquals( "{row={col=value}}", actual.toString() );
    }

    @Test
    public void testOverrideWith_withInitialTable( ) {
        Table<?, ?, ?> actual = builder_withInitial.overrideWith( second ).overrideWith( third ).build();

        assertEquals( 4, actual.size() );
        assertEquals( expected, actual );
        assertEquals( expected.toString(), actual.toString() );
        assertEquals( "{={col3=value in third}, row1={col1=value in second, col2=another value in first}, row2={col2=value in third}}",
                      actual.toString() );
    }

    @Test( expected = NullPointerException.class )
    public void testOverrideWith_doesNotAccept_nullableRow( ) {
        builder_noInitial.overrideWith( null, "col", "val" );
    }

    @Test( expected = NullPointerException.class )
    public void testOverrideWith_doesNotAccept_nullableColumn( ) {
        builder_noInitial.overrideWith( "row", null, "val" );
    }

    @Test( expected = NullPointerException.class )
    public void testOverrideWith_doesNotAccept_nullableValue( ) {
        builder_noInitial.overrideWith( "row", "col", null );
    }

    @Test
    public void testBuild_returns_differentObjects( ) {
        Table<?, ?, ?> actual1 = builder_withInitial.build();
        Table<?, ?, ?> actual2 = builder_withInitial.build();

        assertNotSame( actual1, actual2 );
        assertFalse( actual1.isEmpty() );
        assertFalse( actual2.isEmpty() );
        assertEquals( actual1.size(), actual2.size() );
        assertEquals( actual1, actual2 );

        assertEquals( actual1.toString(), actual2.toString() );
        assertEquals( "{row1={col1=value in first, col2=another value in first}}", actual1.toString() );
    }

    @Test
    public void testOverrideWith_acceptsNullableTable( ) {
        builder_noInitial.overrideWith( null );

        Table<?, ?, ?> actual = builder_noInitial.build();

        assertEquals( 0, actual.size() );
        assertTrue( actual.isEmpty() );

        assertEquals( "{}", actual.toString() );
    }

    @Test
    public void testBuild_returnsImmutableInstance( ) {
        assertTrue( builder_noInitial.build() instanceof ImmutableTable );
    }
}
