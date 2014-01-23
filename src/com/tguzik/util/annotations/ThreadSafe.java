/**
 * 
 */
package com.tguzik.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the type or method can be safely used from different threads
 * at the same time. The type or method either handles synchronization itself or
 * does not have mutable state.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 * @see {@link SingleThreaded} {@link Immutable}
 */
@Retention( RetentionPolicy.SOURCE )
@Target( {ElementType.TYPE, ElementType.METHOD} )
public @interface ThreadSafe {

}
