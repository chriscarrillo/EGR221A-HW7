package datastructures.worklists;

import egr221a.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See egr221a/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private static final int DEFAULT_CAPACITY = 256;
    private static final int CHILDREN = 4;
    private int size;
    private int level;

    public MinFourHeap() {
        clear();
    }

    @Override
    public boolean hasWork() {
        return size != 0;
    }

    @Override
    public void add(E work) {
        if(size == data.length){
            level++;
            E[] largeHeap = (E[]) (new Comparable[(int) (data.length + Math.pow(CHILDREN,level))]);
            for(int i = 0; i < data.length; i++){
                largeHeap[i] = data[i];
            }
            data = largeHeap;
        }
        data[size] = work;

        int i = size;
        while ( i != 0 && data[i].compareTo(data[(i-1)/CHILDREN]) < 0){
            swap(data, i, (i - 1)/CHILDREN);
            i = (i-1)/CHILDREN;
        }

        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException("Does not have work");
        }
        return data[0];
    }

    @Override
    public E next() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }
        E temp = data[0];
        data[0] = null;
        swap(data, 0, size - 1);
        size--;

        int i = 0;
        int minOfFour = findMin(data, i);

        while (minOfFour != -1 && data[i].compareTo(data[minOfFour]) > 0) {
            swap(data, i, minOfFour);

            i = minOfFour;
            minOfFour = findMin(data, i);
        }

        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        data = (E[])new Comparable[DEFAULT_CAPACITY];
        level = 2;
        size = 0;
    }
    private void swap(E[] data, int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
    private int findMin(E[] data, int i) {
        if (CHILDREN * i + 4 >= size) {
            if (CHILDREN * i + 1 >= size) {
                return -1;
            } else if (CHILDREN * i + 2 >= size) {
                return CHILDREN * i + 1;
            } else if (CHILDREN * i + 3 >= size) {
                int smaller = data[CHILDREN * i + 1].compareTo(data[CHILDREN * i + 2]) <= 0
                        ? (CHILDREN * i + 1) : (CHILDREN * i + 2);
                return smaller;
            } else {
                int smaller = data[CHILDREN * i + 1].compareTo(data[CHILDREN * i + 2]) <= 0
                        ? (CHILDREN * i + 1) : (CHILDREN * i + 2);
                int smallest = data[smaller].compareTo(data[CHILDREN * i + 3]) <= 0
                        ? smaller : (CHILDREN * i + 3);
                return smallest;
            }
        } else {
            int indexOfSmallerOfLeftTwo = data[CHILDREN * i + 1].compareTo(data[CHILDREN * i + 2]) <= 0
                    ? (CHILDREN * i + 1) : (CHILDREN * i + 2);
            int indexOfSmallerOfRightTwo = data[CHILDREN * i + 3].compareTo(data[CHILDREN * i + 4]) <= 0
                    ? (CHILDREN * i + 3) : (CHILDREN * i + 4);
            int indexOfSmallestOfFour = data[indexOfSmallerOfLeftTwo].compareTo(data[indexOfSmallerOfRightTwo]) <= 0
                    ? indexOfSmallerOfLeftTwo : indexOfSmallerOfRightTwo;
            return indexOfSmallestOfFour;
        }
    }
}
