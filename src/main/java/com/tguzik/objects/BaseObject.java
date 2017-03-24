package com.tguzik.objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

import com.tguzik.annotations.ExpectedPerformanceProfile;
import com.tguzik.annotations.ExpectedPerformanceProfile.Kind;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Convenience base class for objects that are not constantly compared or
 * converted to string. This class should be used when overall performance does
 * not matter as much.
 *
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 * @see com.tguzik.objects.PerformanceAwareBaseObject
 * @since 0.1
 */
@ParametersAreNonnullByDefault
@ExpectedPerformanceProfile( value = Kind.REFLECTION_HEAVY )
public abstract class BaseObject {
    public static final MultilineNoAddressStyle MULTILINE_NO_ADDRESS_STYLE = new MultilineNoAddressStyle();

    /** Ignores transient fields */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode( this, false );
    }

    /** Ignores transient fields. Always returns false when parameter class doesn't match exactly */
    @Override
    public boolean equals( Object other ) {
        return other != null && sameClass( other ) && EqualsBuilder.reflectionEquals( this, other, false );
    }

    private boolean sameClass( Object other ) {
        return Objects.equals( this.getClass(), other.getClass() );
    }

    /** @see #toString(Object, org.apache.commons.lang3.builder.ToStringStyle) */
    @Nonnull
    @Override
    public String toString() {
        return toString( ToStringStyle.SHORT_PREFIX_STYLE );
    }

    /**
     * @param style
     *         the style to be used when converting the class to string
     *
     * @return Empty string if object was null, string representation obtained via reflection otherwise.
     * @see #toString(Object, org.apache.commons.lang3.builder.ToStringStyle)
     */
    @Nonnull
    public String toString( ToStringStyle style ) {
        return toString( this, style );
    }

    /**
     * Convenience function that produces a string representation of object instance's fields using selected
     * ToStringStyle. Static and transient fields are not printed.
     *
     * @param object
     *         the object to be converted to string using reflection
     * @param style
     *         the style to be used when converting the class to string
     *
     * @return Empty string if object was null, string representation obtained via reflection otherwise.
     */
    @Nonnull
    public static String toString( @Nullable Object object, ToStringStyle style ) {
        if ( style == null ) {
            // JDK8 would make a bit shorter..
            throw new NullPointerException( "To string style parameter cannot be null!" );
        }

        if ( object == null ) {
            return StringUtils.EMPTY;
        }

        return ReflectionToStringBuilder.toString( object, style, false, false );
    }
}

