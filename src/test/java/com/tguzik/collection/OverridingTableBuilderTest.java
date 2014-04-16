package com.tguzik.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.tguzik.collection.OverridingTableBuilder;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * 
 */
public class OverridingTableBuilderTest
{
    private OverridingTableBuilder<String, String, String> builder_noInitial;
    private OverridingTableBuilder<String, String, String> builder_withInitial;

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

        builder_noInitial = OverridingTableBuilder.create();
        builder_withInitial = OverridingTableBuilder.create( first );
    }

    @Test
    public void testCreate_withInitialTable( ) {
        Table<?, ?, ?> actual = builder_withInitial.build();

        assertEquals( 2, actual.size() );
        assertEquals( "value in first", actual.get( "row1", "col1" ) );
        assertEquals( "another value in first", actual.get( "row1", "col2" ) );
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

        assertEquals( "value in third", actual.get( "", "col3" ) );
        assertEquals( "value in second", actual.get( "row1", "col1" ) );
        assertEquals( "another value in first", actual.get( "row1", "col2" ) );
        assertEquals( "value in third", actual.get( "row2", "col2" ) );
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

        assertEquals( "value in third", actual.get( "", "col3" ) );
        assertEquals( "value in second", actual.get( "row1", "col1" ) );
        assertEquals( "another value in first", actual.get( "row1", "col2" ) );
        assertEquals( "value in third", actual.get( "row2", "col2" ) );
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
        assertEquals( actual1.size(), actual2.size() );

        assertFalse( actual1.isEmpty() );
        assertEquals( 2, actual1.size() );
        assertEquals( "value in first", actual1.get( "row1", "col1" ) );
        assertEquals( "another value in first", actual1.get( "row1", "col2" ) );

        assertFalse( actual2.isEmpty() );
        assertEquals( 2, actual2.size() );
        assertEquals( "value in first", actual2.get( "row1", "col1" ) );
        assertEquals( "another value in first", actual2.get( "row1", "col2" ) );

        assertEquals( actual1, actual2 );
        assertEquals( actual1.toString(), actual2.toString() );
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
    public void testBuild_returnsMutableInstance( ) {
        assertTrue( builder_noInitial.build() instanceof HashBasedTable );
    }
}
