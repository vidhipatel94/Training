package com.example.vidhipatel.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by vidhi.patel on 7/1/2015.
 */
public class MyList<E> extends MyCollection<E> implements List<E> {

    @Override
    public void add(int location, E object) {
        if (toc >= location) {
            if (size() == collection.length)
                extendArray();
            for (int i = size(); i > location; i--) {
                collection[i] = collection[i - 1];
            }
        }
        collection[location] = object;
        toc++;
    }

    @Override
    public boolean addAll(int location, Collection<? extends E> collection) {
        Iterator<E> iterator = (Iterator<E>) collection.iterator();
        while (iterator.hasNext()) {
            E object = iterator.next();
            add(location++, object);
        }
        return true;
    }

    @Override
    public E get(int location) {
        return collection[location];
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i <= toc; i++) {
            if (collection[i].equals((E) object))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        for (int i = toc; i >= 0; i--) {
            if (collection[i].equals((E) object))
                return i;
        }
        return -1;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator() {
        ListIterator<E> iterator = new ListIterator<E>() {
            int index = -1;

            @Override
            public void add(E object) {
                if (toc >= index) {
                    if (size() == collection.length)
                        extendArray();
                    for (int i = size(); i > index; i--) {
                        collection[i] = collection[i - 1];
                    }
                }
                collection[index] = object;
                toc++;
            }

            @Override
            public boolean hasNext() {
                return index<toc && collection[index+1]!=null;
            }

            @Override
            public boolean hasPrevious() {
                return index>0 && collection[index-1]!=null;
            }

            @Override
            public E next() {
                return collection[++index];
            }

            @Override
            public int nextIndex() {
                return index+1;
            }

            @Override
            public E previous() {
                return collection[--index];
            }

            @Override
            public int previousIndex() {
                return index-1;
            }

            @Override
            public void remove() {
                if(index<0 || index>=size())
                    throw new ArrayIndexOutOfBoundsException("Index out of bound");

                //shift
                for (int j = index; j < size() - 1; j++)
                    collection[j] = collection[j + 1];
                collection[toc--] = null;
            }

            @Override
            public void set(E object) {
                if(index<0 || index>=size())
                    throw new ArrayIndexOutOfBoundsException("Index out of bound");
                collection[index]=object;
            }

        };
        return iterator;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(final int location) {
        ListIterator<E> iterator = new ListIterator<E>() {
            int index = location;

            @Override
            public void add(E object) {
                if (toc >= index) {
                    if (size() == collection.length)
                        extendArray();
                    for (int i = size(); i > index; i--) {
                        collection[i] = collection[i - 1];
                    }
                }
                collection[index] = object;
                toc++;
            }

            @Override
            public boolean hasNext() {
                return index<toc && collection[index+1]!=null;
            }

            @Override
            public boolean hasPrevious() {
                return index>0 && collection[index-1]!=null;
            }

            @Override
            public E next() {
                return collection[++index];
            }

            @Override
            public int nextIndex() {
                return index+1;
            }

            @Override
            public E previous() {
                return collection[--index];
            }

            @Override
            public int previousIndex() {
                return index-1;
            }

            @Override
            public void remove() {
                if(index<0 || index>=size())
                    throw new ArrayIndexOutOfBoundsException("Index out of bound");

                //shift
                for (int j = index; j < size() - 1; j++)
                    collection[j] = collection[j + 1];
                collection[toc--] = null;
            }

            @Override
            public void set(E object) {
                if(index<0 || index>=size())
                    throw new ArrayIndexOutOfBoundsException("Index out of bound");
                collection[index]=object;
            }

        };
        return iterator;
    }

    @Override
    public E remove(int location) {
        if(location<0 || location>=size())
            throw new ArrayIndexOutOfBoundsException("Index out of bound");
        E obj=collection[location];
        //shift
        for (int j = location; j < size() - 1; j++)
            collection[j] = collection[j + 1];
        collection[toc--] = null;

        return obj;
    }

    @Override
    public E set(int location, E object) {
        if(location<0 || location>=size())
            throw new ArrayIndexOutOfBoundsException("Index out of bound");
        E obj=collection[location];
        collection[location]=object;
        return obj;
    }

    @NonNull
    @Override
    public List<E> subList(int start, int end) {
        if(start<0 || start>end || end>size())
            throw new IndexOutOfBoundsException("Index out of bound");
        List<E> list=new MyList<E>();
        for(int i=start;i<end;i++)
            list.add(collection[i]);
        return list;
    }
}
