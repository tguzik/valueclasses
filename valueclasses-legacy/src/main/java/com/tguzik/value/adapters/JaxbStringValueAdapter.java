package com.tguzik.value.adapters;

import com.tguzik.traits.HasValue;
import org.jspecify.annotations.NullMarked;

/**
 * Convenience class for typed values where the underlying type is a string.
 *
 * @see <a href="https://eclipse-foundation.blog/2019/05/03/jakarta-ee-java-trademarks/">Migration from javax to jakarta ns</a>
 * @since 0.3
 * @deprecated Indirectly relies on the {@code javax.xml.*} namespace
 */
@NullMarked
@Deprecated( since = "2.0" )
public abstract class JaxbStringValueAdapter<T extends HasValue<String>> extends JaxbValueAdapter<String, T> {
  // Nothing defined.
}
