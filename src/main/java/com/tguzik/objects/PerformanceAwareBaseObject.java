package com.tguzik.objects;

/**
 * Base class for data objects that need to be performance aware. Compared to
 * the {@link BaseObject} this class forces implementations to define their own
 * {@link #equals(Object)} and {@link #hashCode()} instead of relying on
 * reflection mechanisms.
 * <br>
 * The assumption is that method {@link #toString()} is called infrequently and
 * can still rely on reflection mechanisms. If you need this function to be
 * faster, please provide your own implementation (basing the implementation on
 * {@link StringBuilder} is recommended).
 *
 * @author <a href="mailto:tomek+github@tguzik.com">Tomasz Guzik</a>
 * @since 0.3
 */
public abstract class PerformanceAwareBaseObject extends BaseObject {
  @Override
  public abstract boolean equals( Object other );

  @Override
  public abstract int hashCode();
}
