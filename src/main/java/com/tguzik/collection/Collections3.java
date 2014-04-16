package com.tguzik.collection;

import static com.tguzik.collection.Safe.safe;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tguzik.annotations.RefactorThis;
import com.tguzik.collection.CollectionDifference.CollectionDifferenceElement;
import com.tguzik.collection.CollectionDifference.ElementType;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 * 
 */
public class Collections3
{
    @RefactorThis( {"Add a way to distinguish between a single element or a group of elements being added or moved forward or backward on a list",
                    "Perhaps additional tag ADDED_IN_LEFT or REMOVED_FROM_LEFT would do the trick?"} )
    public static < T > CollectionDifference<T> difference( Collection<T> left, Collection<T> right ) {
        // Create references that are not null
        Collection<T> safeLeft = safe( left );
        Collection<T> safeRight = safe( right );

        // Create lists with elements for {A-B}, {B-A} and {A-(A-B)} sets
        List<T> onlyInLeft = Lists.newArrayList( safeLeft );
        onlyInLeft.removeAll( safeRight );

        List<T> onlyInRight = Lists.newArrayList( safeRight );
        onlyInRight.removeAll( safeLeft );

        List<T> inBoth = Lists.newArrayList( safeLeft );
        inBoth.removeAll( onlyInLeft );

        // Create a single list with all elements marked by their origin
        ImmutableList.Builder<CollectionDifferenceElement<T>> markedElements = ImmutableList.builder();
        Iterator<T> leftIterator = safeLeft.iterator();
        Iterator<T> rightIterator = safeRight.iterator();

        /* Let's say that LEFT={a, b, c, d, e}, and RIGHT={a, b, e, f, d}. The plain is to proceed through both
         * collections adding elements from each to a single list with appropriate mark.
         * 
         * Sample:
         * 1. Take L/a and R/a. It's in both collections and they are equal to each other, so add it with IN_BOTH type.
         * 2. Take L/b and R/b. Same story, add them to both collections.
         * 3. Take L/c and R/e. L/c is only in left so add it with ONLY_IN_LEFT. Then reflect that R/e is also in both, but is 
         *    different from L/c, so add it with IN_BOTH as a second element in this iteration.
         * 4. Take L/d and R/f. Add L/d with IN_BOTH. Add R/f with ONLY_IN_RIGHT.
         * 5. Take L/e and R/d. Add L/e with IN_BOTH. Add R/d with IN_BOTH.
         */
        while ( leftIterator.hasNext() || rightIterator.hasNext() ) {
            T fromLeft = leftIterator.hasNext() ? leftIterator.next() : null;
            T fromRight = rightIterator.hasNext() ? rightIterator.next() : null;

            if ( onlyInLeft.contains( fromLeft ) ) {
                markedElements.add( new CollectionDifferenceElement<T>( ElementType.ONLY_IN_LEFT, fromLeft ) );
            }
            if ( inBoth.contains( fromLeft ) ) {
                markedElements.add( new CollectionDifferenceElement<T>( ElementType.IN_BOTH, fromLeft ) );
            }
            if ( inBoth.contains( fromRight ) && !Objects.equal( fromLeft, fromRight ) ) {
                markedElements.add( new CollectionDifferenceElement<T>( ElementType.IN_BOTH, fromRight ) );
            }
            if ( onlyInRight.contains( fromRight ) ) {
                markedElements.add( new CollectionDifferenceElement<T>( ElementType.ONLY_IN_RIGHT, fromRight ) );
            }
        }

        return new CollectionDifference<T>( markedElements.build() );
    }
}
