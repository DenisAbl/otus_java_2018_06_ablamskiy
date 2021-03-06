import java.util.*;

public class MyArrayList<T> implements List<T> {

    private final int INITIAL_SIZE = 24;

    private int size = 0;
    private T[] arrayListElements;

    public MyArrayList(){

        arrayListElements = (T[]) new Object[INITIAL_SIZE];
    }

    public MyArrayList(int presetSize){
        arrayListElements = (T[]) new Object[presetSize];
    }

    public boolean add(T element) {
        if(size == arrayListElements.length) expandArray();
        arrayListElements[size] = element;
        size++;
        return true;
    }

    private void expandArray() {
        if (arrayListElements.length > Integer.MAX_VALUE/2) {
            arrayListElements = Arrays.copyOf(arrayListElements,Integer.MAX_VALUE);
        }
        arrayListElements = Arrays.copyOf(arrayListElements,(arrayListElements.length/2)*3);
    }

    public T get(int index) {
        if(index < size())
        return arrayListElements[index];
        else throw new NoSuchElementException();
    }

    public int size() {
        return size;
    }

    public T remove(int index) {
        T removableElement = arrayListElements[index];
        int tmpIndex = index;
        while(tmpIndex <= size()) {
            arrayListElements[tmpIndex]=arrayListElements[tmpIndex+1];
            tmpIndex++;
        }
        arrayListElements[size-1] = null;
        size--;
        return removableElement;
    }

    public T[] toArray() {
        return Arrays.copyOfRange(arrayListElements,0, size());
    }

    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    public Iterator<T> iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements ListIterator<T>{

        private int currentPosition;

        MyListIterator(){
            this.currentPosition = -1;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < size()-1;
        }

        @Override
        public T next() {
            if (hasNext()){
                return (arrayListElements[++currentPosition]);
            }
            else throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return currentPosition > 0;
        }

        @Override
        public T previous() {
            if (hasPrevious()){
                return arrayListElements[--currentPosition];}
            else throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return ++currentPosition;
        }

        @Override
        public int previousIndex() {
            return --currentPosition;
        }

        @Override
        public void set(T t) {
            if (currentPosition != -1){
            arrayListElements[currentPosition] = t;}
            else throw new NullPointerException();
        }
// Нереализованные методы итератора
        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


//Нереализованные методы для MyArrayList

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() { throw new UnsupportedOperationException(); }

    public boolean contains(Object o) {
        return false;
    }

    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    public Object set(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    public void add(int index, Object element) { throw new UnsupportedOperationException(); }

    public boolean remove(Object o) { throw new UnsupportedOperationException(); }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException();
    }

}



