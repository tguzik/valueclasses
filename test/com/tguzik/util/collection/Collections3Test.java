package com.tguzik.util.collection;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * 
 */
public class Collections3Test
{
    private ImmutableList<String> collectionA;
    private ImmutableList<String> collectionB;

    @Before
    public void setUp( ) throws Exception {
        collectionA = ImmutableList.of( "A", "B", "C", "D", "E", "F" );
        collectionB = ImmutableList.of( "A", "C", "F", "D", "G", "H", "F" );
    }

    @Test
    public void testDifference( ) {
        CollectionDifference<String> diff = Collections3.difference( collectionA, collectionB );
        assertEquals( "", diff.toString() );
    }
}
