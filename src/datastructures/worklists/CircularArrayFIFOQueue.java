package datastructures.worklists;

import egr221a.exceptions.NotYetImplementedException;
import egr221a.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * See egr221a/interfaces/worklists/FixedSizeLIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    private E[] array;
    private int front;
    private int back;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        clear();
    }

    @Override
    public void add(E work) {
        if (isFull()) throw new IllegalStateException("The array is full.");
        array[back] = work;
        back = (back + 1) % capacity();
        size++;
    }

    @Override
    public E peek() {
        return peek(0);
    }
    
    @Override
    public E peek(int i) {
        if (!hasWork()) throw new NoSuchElementException("There is no work.");
        if (i < 0 || i >= size()) throw new IndexOutOfBoundsException("Out of bounds.");
        return array[(front + i) % capacity()];
    }
    
    @Override
    public E next() {
        if (!hasWork()) throw new NoSuchElementException("There is no work.");
        E temp = array[front];
        array[front] = null;
        size--;
        front = (front + 1) % capacity();
        return temp;
    }
    
    @Override
    public void update(int i, E value) {
        if (hasWork()) throw new NoSuchElementException("There is no work.");
        if (i < 0 || i >= size()) throw new IndexOutOfBoundsException("Out of bounds.");
        array[(front + i) % capacity()] = value;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        array = (E[]) new Object[super.capacity()];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in p2. Leave this method unchanged for p1. - DONE
        if (this.size() < other.size()) {
            return -1;
        } else if (this.size() > other.size()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in p2. Leave this method unchanged for p1. - DONE
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here - DONE
            if (size() != other.size()) {
                return false;
            } else {
                for (int i = 0; i < size(); i++) {
                    if (!peek(i).equals(other.peek(i))) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in p2. Leave this method unchanged for p1. - DONE
        int result = 1;
        result = result * 31 + Arrays.hashCode(array);
        result = result * 31 + front;
        result = result * 31 + back;
        result = result * 31 + size;
        return result;
    }
}
