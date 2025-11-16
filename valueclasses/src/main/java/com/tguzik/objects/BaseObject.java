package com.tguzik.objects;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Convenience base class for objects that are not constantly compared or
 * converted to string. This class should be used when overall performance does
 * not matter as much.
 *
 * @author Tomasz Guzik
 * @see com.tguzik.objects.PerformanceAwareBaseObject
 * @since 0.1
 */
@NullMarked
public class BaseObject {
  public static final MultilineNoAddressStyle MULTILINE_NO_ADDRESS_STYLE = new MultilineNoAddressStyle();

  protected BaseObject() {
  }

  /**
   * Ignores transient fields
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode( this, false );
  }

  /**
   * Ignores transient fields. Always returns false when parameter class doesn't match exactly
   */
  @Override
  @SuppressWarnings( "EqualsGetClass" )
  public boolean equals( @Nullable final Object other ) {
    if ( this == other ) {
      return true;
    }
    if ( null == other ) {
      return false;
    }

    // See the comment in Value#equals() for why we are requiring exact class match
    return Objects.equals( this.getClass(), other.getClass() ) && //
           EqualsBuilder.reflectionEquals( this, other, false );
  }

  /**
   * @see #toString(Object, org.apache.commons.lang3.builder.ToStringStyle)
   */
  @Override
  public String toString() {
    return toString( ToStringStyle.SHORT_PREFIX_STYLE );
  }

  /**
   * @param style the style to be used when converting the class to string
   * @return Empty string if object was null, string representation obtained via reflection otherwise.
   * @see #toString(Object, org.apache.commons.lang3.builder.ToStringStyle)
   */
  public String toString( final ToStringStyle style ) {
    return toString( this, style );
  }

  /**
   * Convenience function that produces a string representation of object instance's fields using selected
   * ToStringStyle. Static and transient fields are not printed.
   *
   * @param object the object to be converted to string using reflection
   * @param style  the style to be used when converting the class to string
   * @return Empty string if object was null, string representation obtained via reflection otherwise.
   */
  public static String toString( @Nullable final Object object, final ToStringStyle style ) {
    Objects.requireNonNull( style, "To string style parameter cannot be null!" );

    if ( object == null ) {
      return StringUtils.EMPTY;
    }

    return ReflectionToStringBuilder.toString( object, style, false, false );
  }
}

