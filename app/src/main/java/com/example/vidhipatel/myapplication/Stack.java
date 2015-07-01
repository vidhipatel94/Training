package com.example.vidhipatel.myapplication;

/**
 * Created by vidhi.patel on 7/1/2015.
 */
public interface Stack<T> {
    public void push(T s);
    public T pop();
    public void clear();
    public int tos();
}
