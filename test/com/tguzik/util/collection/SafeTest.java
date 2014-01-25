package com.tguzik.util.collection;

import static com.tguzik.util.collection.Safe.safe;
import static org.junit.Assert.assertSame;

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
    public void testSafe_List( ) {
        assertSame( list, safe( list ) );
    }

    @Test
    public void testSafe_List_nullArgument( ) {
        assertSame( ImmutableList.of(), safe( (List<?>) null ) );
    }

    @Test
    public void testSafe_Set( ) {
        assertSame( set, safe( set ) );
    }

    @Test
    public void testSafe_Set_nullArgument( ) {
        assertSame( ImmutableSet.of(), safe( (Set<?>) null ) );
    }

    @Test
    public void testSafe_Map( ) {
        assertSame( map, safe( map ) );
    }

    @Test
    public void testSafe_Map_nullArgument( ) {
        assertSame( ImmutableMap.of(), safe( (Map<?, ?>) null ) );
    }

    @Test
    public void testSafe_Table( ) {
        assertSame( table, safe( table ) );
    }

    @Test
    public void testSafe_Table_nullArgument( ) {
        assertSame( ImmutableTable.of(), safe( (Table<?, ?, ?>) null ) );
    }
}
