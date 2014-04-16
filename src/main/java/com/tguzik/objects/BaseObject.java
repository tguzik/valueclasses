package com.tguzik.objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.tguzik.annotations.ExpectedPerformanceProfile;
import com.tguzik.annotations.ExpectedPerformanceProfile.PerformanceCharacteristic;

/**
 * Convenience base class for objects that are not constantly compared or
 * converted to string. This class should be used when overall performance does
 * not matter as much.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@ExpectedPerformanceProfile( value = PerformanceCharacteristic.REFLECTION_HEAVY )
public abstract class BaseObject
{
    public static final MultilineNoAddressessToStringStyle MULTILINE_NO_ADDRESS_TOSTRING_STYLE = new MultilineNoAddressessToStringStyle();

    @Override
    public boolean equals( @Nullable Object other ) {
        return EqualsBuilder.reflectionEquals( this, other, false );
    }

    @Nonnull
    @Override
    public String toString( ) {
        return toString( ToStringStyle.SHORT_PREFIX_STYLE );
    }

    @Nonnull
    public String toString( @Nonnull ToStringStyle style ) {
        return ReflectionToStringBuilder.toString( this, style );
    }

    @Override
    public int hashCode( ) {
        return HashCodeBuilder.reflectionHashCode( this, false );
    }
}

class MultilineNoAddressessToStringStyle extends ToStringStyle
{
    private static final long serialVersionUID = 1L;

    MultilineNoAddressessToStringStyle() {
        super();
        this.setContentStart( "[" );
        this.setUseShortClassName( true );
        this.setUseIdentityHashCode( false );
        this.setFieldSeparator( SystemUtils.LINE_SEPARATOR + "  " );
        this.setFieldSeparatorAtStart( true );
        this.setContentEnd( SystemUtils.LINE_SEPARATOR + "]" );
    }
}
