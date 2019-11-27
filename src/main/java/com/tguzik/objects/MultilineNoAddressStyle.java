package com.tguzik.objects;

import org.apache.commons.lang3.builder.ToStringStyle;

public class MultilineNoAddressStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;

    public MultilineNoAddressStyle() {
        super();
        this.setContentStart( "[" + System.lineSeparator() + "  " );
        this.setUseShortClassName( true );
        this.setUseIdentityHashCode( false );
        this.setFieldSeparator( "," + System.lineSeparator() + "  " );
        this.setFieldSeparatorAtStart( false );
        this.setContentEnd( System.lineSeparator() + "]" );
    }
}
