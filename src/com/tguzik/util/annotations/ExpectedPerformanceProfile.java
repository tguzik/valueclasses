package com.tguzik.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

import com.google.common.annotations.Beta;

/**
 * Communicates the expected performance characteristic to the programmer using
 * the class.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Beta
@Retention( RetentionPolicy.CLASS )
@Target( {ElementType.TYPE, ElementType.METHOD} )
public @interface ExpectedPerformanceProfile {
    PerformanceCharacteristic value() default PerformanceCharacteristic.UNKNOWN;

    String[] comment() default StringUtils.EMPTY;

    enum PerformanceCharacteristic {
        CPU_BOUND,
        IO_BOUND,
        MEMORY_BOUND,
        CACHE_BOUND,
        REFLECTION_HEAVY,
        LOW_LATENCY,
        UNKNOWN;
    }
}
