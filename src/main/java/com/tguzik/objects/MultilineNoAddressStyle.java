package com.tguzik.objects;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class MultilineNoAddressStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;

    MultilineNoAddressStyle() {
        this.setContentStart( "[" + SystemUtils.LINE_SEPARATOR + "  " );
        this.setUseShortClassName( true );
        this.setUseIdentityHashCode( false );
        this.setFieldSeparator( "," + SystemUtils.LINE_SEPARATOR + "  " );
        this.setFieldSeparatorAtStart( false );
        this.setContentEnd( SystemUtils.LINE_SEPARATOR + "]" );
    }
}
