package com.tguzik.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Indicates that the following piece of code should be refactored. The details
 * of what and how should be refactored are indicated in the value of this
 * annotation.
 * </p>
 * <p>
 * The reason why this exists is that sometimes there's no time or resources at
 * hand to rewrite something to be done right, so one would have to resort to
 * leaving a memento. Yes, in the perfect world that situation shouldn't happen,
 * but in reality happens more than one would like. Especially with old
 * codebases.
 * </p>
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Retention( RetentionPolicy.SOURCE )
@Target( value = {ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR} )
public @interface RefactorThis {
    /** Programmer's comments about what should be refactored */
    String[] value() default "";
}
