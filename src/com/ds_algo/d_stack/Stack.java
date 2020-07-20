package com.ds_algo.d_stack;
import com.ds_algo.d_stack.list.ArrayList;

/**
 * 组合的设计模式--相比于继承而言,可以避免暴露太多的接口
 * @param <T>
 */
public class Stack<T> {

    private ArrayList<T> list = new ArrayList<>();

    public int size(){
        return list.size();
    }
    public boolean isEmpty(){
        return list.isEmpty();
    }
    public void push(T element){
        list.add(element);
    }
    public T pop(){
        return list.remove(list.size()-1);
    }
    public T top(){
        return list.get(list.size()-1);
    }

}
