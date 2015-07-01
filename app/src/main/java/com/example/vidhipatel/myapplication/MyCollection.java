package com.example.vidhipatel.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by vidhi.patel on 7/1/2015.
 */
public class MyCollection<E> implements Collection<E> {
    E[] collection;
    public static int SIZE=2;
    int toc=-1;

    public MyCollection(){
        collection = (E[]) new Object[SIZE];
    }

    @Override
    public boolean add(E object) {
        if(toc>=collection.length-1) {
            extendArray();
        }
        collection[++toc] = object;
        return true;
    }

    private void extendArray() {
        E[] collection2= (E[]) new Object[collection.length+SIZE];
        System.arraycopy(collection,0,collection2,0,collection.length);
        collection=collection2;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Iterator<E> iterator = (Iterator<E>) collection.iterator();
        while(iterator.hasNext()){
            E object = iterator.next();
            if(!add(object))
                return false;
        }
        return true;
    }

    @Override
    public void clear() {
        while(toc>=0)
            collection[toc--]=null;
    }

    @Override
    public boolean contains(Object object) {
        for(int i=0;i<size();i++){
            if(collection[i].equals((E) object))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        Iterator<E> iterator= (Iterator<E>) collection.iterator();
        while(iterator.hasNext()){
            if(!contains(iterator.next()))
                return false;
        }
        //return collection.containsAll(collection);
        return true;
    }

    @Override
    public boolean isEmpty() {
        if(toc<0)
            return true;
        return false;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            int index=0;
            @Override
            public boolean hasNext() {
                return index<=toc && collection[index]!=null;
            }

            @Override
            public E next() {
                return collection[index++];
            }

            @Override
            public void remove() {

            }
        };
        return iterator;
    }

    @Override
    public boolean remove(Object object) {
        for(int i=0;i<size();i++){
            if(collection[i].equals((E) object)) {
                //shift
                for(int j=i;j<size()-1;j++)
                    collection[j]=collection[j+1];
                collection[toc--]=null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Iterator<E> iterator= (Iterator<E>) collection.iterator();
        while(iterator.hasNext()){
            if(!remove(iterator.next()))
                return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Iterator<E> iterator= null;
        boolean hasObject;
        E object=null;
        int index_remove[];
        for(int i=0;i<size();i++){
            iterator= (Iterator<E>) collection.iterator();
            hasObject=false;
            while(iterator.hasNext()){
                object=iterator.next();
                if(this.collection[i].equals(object)){
                    hasObject=true;
                    break;
                }
            }
            if(!hasObject) {
                if (!remove(this.collection[i]))
                    return false;
                i--;
            }
        }

        return true;
    }

    @Override
    public int size() {
        return toc+1;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        Object[] object=new Object[size()];
        System.arraycopy(collection,0,object,0,size());
        return object;
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        if(array.length>=size()){
            System.arraycopy(collection,0,array,0,size());
            for(int i=size();i<array.length;i++){
                array[i]=null;
            }
            return array;
        }
        else {
            T[] newArray
                    = (T[]) Array.newInstance(array.getClass().getComponentType(), size());
            System.arraycopy(collection,0,newArray,0,size());
            return newArray;
        }
    }
}
