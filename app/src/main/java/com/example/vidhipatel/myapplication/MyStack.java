package com.example.vidhipatel.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidhi.patel on 7/1/2015.
 */

public class MyStack<T> implements Stack<T>{

    List<T> stack= new ArrayList<T>();
    public void push(T s){
        stack.add(s);
    }
    public T pop(){
        if(!stack.isEmpty()) {;
            return stack.remove(stack.size()-1);
        }
        else
            throw new IllegalStateException("MyStack underflow");
    }
    public void clear(){
        stack.clear();
    }
    public int tos(){
        return stack.size();
    }

}
