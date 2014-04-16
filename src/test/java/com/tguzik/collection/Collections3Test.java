package com.tguzik.collection;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.tguzik.objects.BaseObject;
import com.tguzik.unittests.Loader;
import com.tguzik.unittests.Normalize;

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
    public void testDifference( ) throws IOException {
        String expected = Loader.loadFile( getClass(), "data", "collection-difference-expected.txt" );
        String actual = Collections3.difference( collectionA, collectionB )
                                    .toString( BaseObject.MULTILINE_NO_ADDRESS_TOSTRING_STYLE );

        assertEquals( expected, Normalize.newLines( actual ) );
    }
}
