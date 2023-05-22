package br.com.shapeup.common.utils;

public class QueueObj<T> {
    private int size;
    private final T[] queue;

    public QueueObj(int capacidade) {
        this.size = 0;
        this.queue = (T[]) new Object[capacidade];
    }

    public boolean isEmpty() { return size == 0; }

    public boolean isFull() { return queue.length == size; }

    public void insert(T info) {
        if (isFull()) {
            throw new RuntimeException();
        }
        queue[size] = info;
        size++;
    }

    public T peek() {
        return queue[0];
    }

    public T poll() {
        T first = queue[0];
        for (int i = 0; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }
        queue[--size] = null;
        return first;
    }

    public int getSize(){
        return size;
    }
}