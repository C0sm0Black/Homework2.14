package com.example.homework2142.domain;

import com.example.homework2142.exception.NotContainsStringException;
import com.example.homework2142.exception.NullException;
import com.example.homework2142.exception.WrongIndexException;

import java.util.stream.IntStream;

public class StringListImpl implements StringList {

    private String[] arr;
    private int size;

    public StringListImpl(int size) {

        this.arr = new String[size];
        this.size = 0;

    }

    @Override
    public String add(String item) {

        checkNullException(item);
        checkForSize();

        arr[size] = item;
        size++;

        return item;

    }

    @Override
    public String add(int index, String item) {

        checkNullException(item);
        checkIndexException(index);
        addWithDisplacement(index, item);

        return item;

    }

    @Override
    public String set(int index, String item) {

        checkNullException(item);
        checkIndexException(index);

        arr[index] = item;

        return item;

    }

    @Override
    public String remove(String item) {

        if (!contains(item)) {
            throw new NotContainsStringException("NotContainsStringException");
        }

        removeWithDisplacement(indexOf(item));

        return item;

    }

    @Override
    public String remove(int index) {

        checkIndexException(index);
        removeWithDisplacement(index);

        return arr[index];

    }

    @Override
    public boolean contains(String item) {

        checkNullException(item);

        for (String s : arr) {

            if (s.equals(item)) {
                return true;
            }

        }

        return false;

    }

    @Override
    public int indexOf(String item) {

        checkNullException(item);

        return IntStream.range(0, size)
                .filter(i -> arr[i].equals(item))
                .findFirst()
                .orElse(-1);

    }

    @Override
    public int lastIndexOf(String item) {

        checkNullException(item);

        return IntStream.iterate(size - 1, i -> i >= 0, i -> i + 1)
                .filter(i -> arr[i].equals(item))
                .findFirst()
                .orElse(-1);

    }

    @Override
    public String get(int index) {

        checkIndexException(index);

        return arr[index];

    }

    @Override
    public boolean equals(StringList otherList) {

        if (size != otherList.size()) {
            return false;
        }

        for (int i = 0; i < size + 1; i++) {

            if (!arr[i].equals(otherList.get(i))) {
                return false;
            }

        }

        return true;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        arr = new String[5];
    }

    @Override
    public String[] toArray() {

        String[] newArr = new String[arr.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);

        return newArr;
    }

    private void checkNullException(String item) {

        if (item == null) {
            throw new NullException("NullException");
        }

    }

    private void checkForSize() {

        if (arr.length - 1 == size) {

            String[] newArr = new String[arr.length * 2 + 1];
            System.arraycopy(arr, 0, newArr, 0, size);
            arr = newArr;

        }

    }

    private void checkIndexException(int index) {

        if (index > size) {
            throw new WrongIndexException("WrongIndexException");
        }

    }

    private void addWithDisplacement(int index, String item) {

        checkForSize();

        String[] newArr = new String[arr.length];

        if (index == 0) {

            System.arraycopy(arr, 0, newArr, 1, size + 1);
            arr = newArr;
            arr[0] = item;
            size++;

        } else if (index == size) {

            arr[index] = item;
            size++;

        } else {

            System.arraycopy(arr, 0, newArr, 0, index);
            System.arraycopy(arr, index, newArr, index + 1, size + 1);
            newArr[index] = item;
            arr = newArr;
            size++;

        }

    }

    private void removeWithDisplacement(int index) {

        String[] newArr = new String[arr.length];

        if (index == 0) {

            System.arraycopy(arr, 0, newArr, 1, size + 1);
            arr = newArr;
            size--;

        } else {

            System.arraycopy(arr, 0, newArr, 0, index);
            System.arraycopy(arr, index + 1, newArr, index, size + 1);
            arr = newArr;
            size--;

        }

    }

    @Override
    public String toString() {

        StringBuilder b = new StringBuilder();
        int iMax = size;
        b.append('[');

        for (int i = 0; i < iMax; i++) {
            b.append(arr[i]);

            if (!(i == iMax - 1)) {
                b.append(", ");
            }

        }

        b.append(']');

        return "StringListImpl {" +
                "arr = " + b.toString() +
                ", size = " + size +
                '}';
    }

}
