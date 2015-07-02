package com.example.vidhipatel.myapplication;

import android.util.Log;

import java.util.Objects;

/**
 * Created by vidhi.patel on 7/1/2015.
 */
public class ArrayStack<T> implements Stack<T>{
    T[] stack;
    int tos=-1;
    public static int STACK_SIZE=2;

    public ArrayStack() {
        stack = (T[]) new Object[STACK_SIZE];
    }

    @Override
    public void push(T s) {
        if(tos>=stack.length-1) {
            extendArray();
        }
        stack[++tos]=s;
    }

    private void extendArray() {
        T[] stack2= (T[]) new Object[stack.length+STACK_SIZE];
        System.arraycopy(stack,0,stack2,0,stack.length);
        stack=stack2;
    }

    @Override
    public T pop() {
        if(tos>=0) {
            T element=stack[tos-1];
            stack[tos--]=null;
            return element;
        }
        else
            throw new IllegalStateException("stack underflow");
    }

    @Override
    public void clear() {
        while(tos>=0){
            stack[tos--]=null;
        }
    }

    @Override
    public int tos() {
        return tos;
    }
}
