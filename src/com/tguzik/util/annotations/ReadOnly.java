package com.tguzik.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Signifies that the value produced by the method should be only read from.
 * Attempting to write is unspecified behavior, may throw exceptions or may
 * damage the internal state of the software.
 * </p>
 * <p>
 * Use with mutable classes and interfaces suggesting mutability. If possible
 * use types/values enforcing immutability.
 * </p>
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */

@Retention( RetentionPolicy.CLASS )
@Target( {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE} )
public @interface ReadOnly {

}
