package br.com.shapeup.common.utils;

public class StackObj<T> {
    private T[] stack;
    private int top;

    public StackObj(int capacidade) {
        stack = (T[]) new Object[capacidade];
        top = -1;
    }

    public Boolean isEmpty() {
        return top == -1;
    }

    public Boolean isFull() {
        return top == stack.length - 1;
    }

    public void push(T info) {
        if (!isFull()) {
            top++;
            stack[top] = info;
            return;
        }

        throw new StackOverflowError();
    }

    public T pop() {
        return stack[top--];
    }

    public T peek() {
        if (isEmpty()) return null;
        return stack[top];
    }

    public int getTop() {
        return top;
    }
}