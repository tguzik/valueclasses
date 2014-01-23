/**
 * 
 */
package com.tguzik.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the type or method should be either used within single thread
 * or synchronized on caller's side.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 * @see {@link ThreadSafe}
 */
@Retention( RetentionPolicy.SOURCE )
@Target( {ElementType.TYPE, ElementType.METHOD} )
public @interface SingleThreaded {

}
