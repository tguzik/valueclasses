package com.tguzik.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates to programmer that the type does not modify its state after it is
 * instantiated.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 * @see {@link ThreadSafe}
 */
@Retention( RetentionPolicy.SOURCE )
@Target( {ElementType.TYPE, ElementType.METHOD} )
public @interface Immutable {

}
