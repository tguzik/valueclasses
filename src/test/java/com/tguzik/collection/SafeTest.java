package com.tguzik.collection;

import static com.tguzik.collection.Safe.safe;
import static org.junit.Assert.assertSame;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class SafeTest
{
    private HashBasedTable<String, String, String> table;
    private Map<String, String> map;
    private List<String> list;
    private Set<String> set;

    @Before
    public void setUp( ) throws Exception {
        table = HashBasedTable.create();
        list = Lists.newArrayList();
        map = Maps.newHashMap();
        set = Sets.newHashSet();
    }

    @Test
    public void testSafe_list( ) {
        assertSame( list, safe( list ) );
    }

    @Test
    public void testSafe_list_nullArgument( ) {
        assertSame( ImmutableList.of(), safe( (List<?>) null ) );
    }

    @Test
    public void testSafe_iterable( ) {
        assertSame( list, safe( (Iterable<?>) list ) );
    }

    @Test
    public void testSafe_iterable_nullArgument( ) {
        assertSame( ImmutableList.of(), safe( (Iterable<?>) null ) );
    }

    @Test
    public void testSafe_collection( ) {
        assertSame( list, safe( (Collection<?>) list ) );
    }

    @Test
    public void testSafe_collection_nullArgument( ) {
        assertSame( ImmutableList.of(), safe( (Collection<?>) null ) );
    }

    @Test
    public void testSafe_set( ) {
        assertSame( set, safe( set ) );
    }

    @Test
    public void testSafe_set_nullArgument( ) {
        assertSame( ImmutableSet.of(), safe( (Set<?>) null ) );
    }

    @Test
    public void testSafe_map( ) {
        assertSame( map, safe( map ) );
    }

    @Test
    public void testSafe_map_nullArgument( ) {
        assertSame( ImmutableMap.of(), safe( (Map<?, ?>) null ) );
    }

    @Test
    public void testSafe_table( ) {
        assertSame( table, safe( table ) );
    }

    @Test
    public void testSafe_table_nullArgument( ) {
        assertSame( ImmutableTable.of(), safe( (Table<?, ?, ?>) null ) );
    }
}
