package org.example.customarraylist;

import java.util.*;

public class CustomArrayList<E>  {
    private static final int INITIAL_SIZE = 20;
    private static final double INCREASING_COEFFICIENT = 1.5;
    private E[] dataArray;
    private int arrayListSize;

    @SuppressWarnings("unchecked")
    public CustomArrayList() {
        dataArray = (E[]) new Object[INITIAL_SIZE];
        arrayListSize = 0;
    }

    public CustomArrayList(E[] array) {
        if (array.length >= INITIAL_SIZE)
            dataArray = Arrays.copyOf(array, array.length);
        else
            dataArray = Arrays.copyOf(array, INITIAL_SIZE);

        arrayListSize = array.length;
    }

    public void add(int index, E element) {
        if (index > arrayListSize || index < 0)
            throw new IndexOutOfBoundsException(index);

        increaseSize(1 + arrayListSize);
        System.arraycopy(dataArray, index,
                dataArray, index + 1,
                arrayListSize - index);
        dataArray[index] = element;
        arrayListSize++;
    }

    public void add(E element) {
        add(arrayListSize, element);
    }

    public void addAll(Collection<? extends E> c) {
        Object[] addingArray = c.toArray();
        if (addingArray.length == 0)
            return;
        increaseSize(addingArray.length + arrayListSize);
        System.arraycopy(addingArray, 0, dataArray, arrayListSize, addingArray.length);
        arrayListSize += addingArray.length;
    }

    public void clear() {
        for (int i = 0; i < arrayListSize; i++)
            dataArray[i] = null;
        arrayListSize = 0;
    }

    public E get(int index) {
        if (index >= arrayListSize || index < 0)
            throw new IndexOutOfBoundsException(index);

        return dataArray[index];
    }

    public boolean isEmpty() {
        return arrayListSize == 0;
    }

    public void remove(int index) {
        if (index >= arrayListSize || index < 0)
            throw new IndexOutOfBoundsException(index);
        if (index < arrayListSize - 1)
            System.arraycopy(dataArray, index + 1, dataArray, index, arrayListSize - index - 1);
        dataArray[arrayListSize - 1] = null;
        arrayListSize--;
    }

    public void remove(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Null value is illegal here.");

        for (int i = 0; i < arrayListSize; i++) {
            if (o.equals(dataArray[i])) {
                remove(i);
                break;
            }
        }
    }

    public int size() {
        return arrayListSize;
    }

    public void sort(Comparator<? super E> c) {
        quickSort(dataArray, 0, arrayListSize - 1, c);
    }

    private void quickSort(E[] data, int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int pi = partition(data, low, high, comparator);
            quickSort(data, low, pi - 1, comparator);
            quickSort(data, pi + 1, high, comparator);
        }
    }

    private int partition(E[] data, int low, int high, Comparator<? super E> comparator) {
        E pivot = data[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(data[j], pivot) <= 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, high);
        return i + 1;
    }

    private void swap(E[] data, int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void increaseSize(int newMinSize) {
        if (newMinSize > dataArray.length) {
            E[] newArray;

            if (newMinSize >= dataArray.length * INCREASING_COEFFICIENT)
                newArray = Arrays.copyOf(dataArray, newMinSize);
            else
                newArray = Arrays.copyOf(dataArray, (int) Math.round(dataArray.length * INCREASING_COEFFICIENT));

            dataArray = newArray;
        }
    }
}
