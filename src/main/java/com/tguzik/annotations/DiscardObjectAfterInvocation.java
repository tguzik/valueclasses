package com.tguzik.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: Documentation
 * 
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Target( ElementType.METHOD )
@Retention( RetentionPolicy.CLASS )
public @interface DiscardObjectAfterInvocation {

}
