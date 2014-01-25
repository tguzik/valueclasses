package com.tguzik.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * 
 */
public class OverridingImmutableMapBuilderTest
{
    private OverridingImmutableMapBuilder<String, String> builder_noInitial;
    private OverridingImmutableMapBuilder<String, String> builder_withInitial;

    private Map<String, String> empty;
    private Map<String, String> first;
    private Map<String, String> second;
    private Map<String, String> third;
    private Map<String, String> expected;

    @Before
    public void setUp( ) throws Exception {
        empty = ImmutableMap.of();
        first = ImmutableMap.of( "key1", "value in first", "key2", "another value in first" );
        second = ImmutableMap.of( "key2", "value in second", "", "value in second" );
        third = ImmutableMap.of( "", "value in third", "key3", "value in third" );

        expected = ImmutableMap.of( "",
                                    "value in third",
                                    "key3",
                                    "value in third",
                                    "key2",
                                    "value in second",
                                    "key1",
                                    "value in first" );

        builder_noInitial = OverridingImmutableMapBuilder.create();
        builder_withInitial = OverridingImmutableMapBuilder.create( first );
    }

    @Test
    public void testCreate_withInitialMap( ) {
        Map<?, ?> actual = builder_withInitial.build();

        assertEquals( "value in first", actual.get( "key1" ) );
        assertEquals( "another value in first", actual.get( "key2" ) );
        assertEquals( 2, actual.size() );
    }

    @Test
    public void testCreate_withoutInitialMap( ) {
        Map<?, ?> actual = builder_noInitial.build();

        assertNotNull( actual );
        assertEquals( empty, actual );
        assertTrue( actual.isEmpty() );
        assertEquals( 0, actual.size() );

        assertEquals( "{}", actual.toString() );
    }

    @Test
    public void testOverrideWith_withoutInitialMap( ) {
        Map<?, ?> actual = builder_noInitial.overrideWith( first )
                                            .overrideWith( second )
                                            .overrideWith( third )
                                            .build();

        assertEquals( 4, actual.size() );
        assertEquals( expected, actual );
        assertEquals( expected.toString(), actual.toString() );

        assertEquals( "value in third", actual.get( "" ) );
        assertEquals( "value in third", actual.get( "key3" ) );
        assertEquals( "value in second", actual.get( "key2" ) );
        assertEquals( "value in first", actual.get( "key1" ) );
    }

    @Test
    public void testOverrideWith_explicitTwoArguments( ) {
        Map<?, ?> actual = builder_noInitial.overrideWith( "key", "value" ).build();

        assertEquals( 1, actual.size() );
        assertEquals( "{key=value}", actual.toString() );
    }

    @Test
    public void testOverrideWith_withInitialMap( ) {
        Map<?, ?> actual = builder_withInitial.overrideWith( second ).overrideWith( third ).build();

        assertEquals( 4, actual.size() );
        assertEquals( expected, actual );
        assertEquals( expected.toString(), actual.toString() );

        assertEquals( "value in third", actual.get( "" ) );
        assertEquals( "value in third", actual.get( "key3" ) );
        assertEquals( "value in second", actual.get( "key2" ) );
        assertEquals( "value in first", actual.get( "key1" ) );
    }

    @Test
    public void testOverrideWith_accepts_nullableMap( ) {
        builder_noInitial.overrideWith( null );

        Map<?, ?> actual = builder_noInitial.build();

        assertEquals( 0, actual.size() );
        assertTrue( actual.isEmpty() );

        assertEquals( "{}", actual.toString() );
    }

    @Test( expected = NullPointerException.class )
    public void testOverrideWith_doesNotAccept_nullableKey( ) {
        builder_noInitial.overrideWith( null, "some value" );
    }

    @Test( expected = NullPointerException.class )
    public void testOverrideWith_doesNotAccept_nullableValue( ) {
        builder_noInitial.overrideWith( "key", null );
    }

    @Test( expected = NullPointerException.class )
    public void testOverrideWith_doesNotAccept_mapWithNullKeys( ) {
        Map<String, String> map = Maps.newHashMap();
        map.put( null, "value" );

        builder_noInitial.overrideWith( map );
    }

    @Test( expected = NullPointerException.class )
    public void testOverrideWith_doesNotAccept_mapWithNullValues( ) {
        Map<String, String> map = Maps.newHashMap();
        map.put( "key", null );

        builder_noInitial.overrideWith( map );
    }

    @Test
    public void testBuild_returns_differentObjects( ) {
        Map<?, ?> actual1 = builder_withInitial.build();
        Map<?, ?> actual2 = builder_withInitial.build();

        assertNotSame( actual1, actual2 );
        assertEquals( actual1, actual2 );
        assertEquals( actual1.size(), actual2.size() );

        assertFalse( actual1.isEmpty() );
        assertEquals( 2, actual1.size() );
        assertEquals( "value in first", actual1.get( "key1" ) );
        assertEquals( "another value in first", actual1.get( "key2" ) );

        assertFalse( actual2.isEmpty() );
        assertEquals( 2, actual2.size() );
        assertEquals( "value in first", actual2.get( "key1" ) );
        assertEquals( "another value in first", actual2.get( "key2" ) );

        assertEquals( actual1.toString(), actual2.toString() );
    }

    @Test
    public void testBuild_returnsImmutableInstance( ) {
        assertTrue( builder_noInitial.build() instanceof ImmutableMap );
    }
}
