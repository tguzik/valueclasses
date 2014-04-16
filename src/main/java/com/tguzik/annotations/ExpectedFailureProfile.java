package com.tguzik.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attempts to communicate when user would know that failure happened.
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
@Retention( RetentionPolicy.CLASS )
@Target( {ElementType.TYPE, ElementType.METHOD} )
public @interface ExpectedFailureProfile {
    FailureMode value() default FailureMode.UNKNOWN;

    Transactional transactional() default Transactional.UNKNOWN;

    enum FailureMode {
        FAIL_FAST,
        FAIL_LATE,
        HIDES_FAILURES,
        UNKNOWN
    }

    enum Transactional {
        YES,
        NO,
        UNKNOWN
    }
}
