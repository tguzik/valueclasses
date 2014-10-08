package com.tguzik.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class SettableHashCodeTest {
    private SettableHashCode object;

    @Before
    public void setUp() {
        object = SettableHashCode.create( 42 );
    }

    @Test
    public void testSettableHashCode() {
        assertThat( object.hashCode() ).isEqualTo( 42 ).isEqualTo( object.get().intValue() );
    }
}
