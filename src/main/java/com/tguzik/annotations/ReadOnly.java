package com.tguzik.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * This annotation attempts to communicate to other API users that the value
 * produced by the method or passed as a parameter should be only read from.
 * Attempting to mutate the object is unspecified behavior, may throw exceptions
 * or may damage the internal state of the software.
 * </p>
 * <p>
 * Use with mutable classes and interfaces suggesting mutability. If possible
 * use types/values enforcing immutability.
 * </p>
 * <p>
 * Please note that the <tt>final</tt> keyword only guarantees that the
 * reference will not be changed, but cannot guarantee that the object itself is
 * not changed (mutated).
 * </p>
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */

@Retention( RetentionPolicy.CLASS )
@Target( {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE} )
public @interface ReadOnly {

}
