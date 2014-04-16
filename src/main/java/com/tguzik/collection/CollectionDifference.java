package com.tguzik.collection;

import static com.tguzik.collection.Safe.safe;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.google.common.annotations.Beta;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.tguzik.collection.CollectionDifference.CollectionDifferenceElement;
import com.tguzik.objects.BaseObject;

/**
 * TODO: Documentation
 * 
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
@Beta
@Immutable
public class CollectionDifference< T > extends BaseObject implements Iterable<CollectionDifferenceElement<T>>
{
    private static final ElementFoundInPredicate PREDICATE_ONLY_IN_LEFT = new ElementFoundInPredicate( ElementType.ONLY_IN_LEFT );
    private static final ElementFoundInPredicate PREDICATE_IN_BOTH = new ElementFoundInPredicate( ElementType.IN_BOTH );
    private static final ElementFoundInPredicate PREDICATE_ONLY_IN_RIGHT = new ElementFoundInPredicate( ElementType.ONLY_IN_RIGHT );
    private final ImmutableList<CollectionDifferenceElement<T>> elements;

    public CollectionDifference( @Nullable List<CollectionDifferenceElement<T>> elements ) {
        this.elements = ImmutableList.copyOf( safe( elements ) );
    }

    @Override
    public Iterator<CollectionDifferenceElement<T>> iterator( ) {
        return elements.iterator();
    }

    public Iterable<CollectionDifferenceElement<T>> onlyInLeft( ) {
        return Iterables.filter( elements, PREDICATE_ONLY_IN_LEFT );
    }

    public Iterable<CollectionDifferenceElement<T>> inBoth( ) {
        return Iterables.filter( elements, PREDICATE_IN_BOTH );
    }

    public Iterable<CollectionDifferenceElement<T>> onlyInRight( ) {
        return Iterables.filter( elements, PREDICATE_ONLY_IN_RIGHT );
    }

    public static enum ElementType {
        ONLY_IN_LEFT,
        IN_BOTH,
        ONLY_IN_RIGHT;
    }

    @Immutable
    public static class CollectionDifferenceElement< T > extends BaseObject
    {
        private final ElementType foundIn;
        private final T object;

        CollectionDifferenceElement( ElementType foundIn, @Nullable T object ) {
            this.foundIn = foundIn;
            this.object = object;
        }

        @Nullable
        public T getObject( ) {
            return object;
        }

        public Object getFoundIn( ) {
            return foundIn;
        }
    }

    private static class ElementFoundInPredicate implements Predicate<CollectionDifferenceElement<?>>
    {
        private final ElementType requestedType;

        public ElementFoundInPredicate( ElementType requestedType ) {
            this.requestedType = requestedType;
        }

        @Override
        public boolean apply( CollectionDifferenceElement<?> input ) {
            return requestedType.equals( input.getFoundIn() );
        }
    }
}
