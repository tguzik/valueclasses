package com.tguzik.objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

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
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @see com.tguzik.objects.PerformanceAwareBaseObject
 * @since 0.1
 */
@ParametersAreNonnullByDefault
@ExpectedPerformanceProfile(value = Kind.REFLECTION_HEAVY)
public abstract class BaseObject {
    public static final MultilineNoAddressStyle MULTILINE_NO_ADDRESS_STYLE = new MultilineNoAddressStyle();

    @Override
    public boolean equals( Object other ) {
        if ( other == null ) {
            return false;
        }

        return EqualsBuilder.reflectionEquals( this, other, false );
    }

    @Nonnull
    @Override
    public String toString() {
        return toString( ToStringStyle.SHORT_PREFIX_STYLE );
    }

    @Nonnull
    public String toString( ToStringStyle style ) {
        return toString( this, style );
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode( this, false );
    }

    /**
     * Convenience function that produces a string representation of object instance's fields using selected
     * ToStringStyle. Static and transient fields are not printed.
     *
     * @return Empty string if object was null, string representation obtained via reflection otherwise.
     */
    @Nonnull
    public static String toString( @Nullable Object object, @Nonnull ToStringStyle style ) {
        if ( object == null ) {
            return StringUtils.EMPTY;
        }

        return ReflectionToStringBuilder.toString( object, style, false, false );
    }
}

