package com.tguzik.objects;

import java.io.Serial;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class MultilineNoAddressStyle extends ToStringStyle {
  @Serial
  private static final long serialVersionUID = 1L;

  public MultilineNoAddressStyle() {
    super();

    final String separator = System.lineSeparator();

    this.setContentStart( "[" + separator + "  " );
    this.setUseShortClassName( true );
    this.setUseIdentityHashCode( false );
    this.setFieldSeparator( "," + separator + "  " );
    this.setFieldSeparatorAtStart( false );
    this.setContentEnd( separator + "]" );
  }
}
