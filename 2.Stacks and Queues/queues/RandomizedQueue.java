import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by shizhan on 16/11/8.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int first;
    private int tail;
    private int count;

    public RandomizedQueue() {
        // construct an empty randomized queue
        a = (Item[]) new Object[1];
        first = 0;
        tail = -1;
        count = 0;
    }

    public boolean isEmpty() {
        // is the queue empty?
        return count == 0;
    }

    public int size() {
        // return the number of items on the queue
        return count;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) throw new NullPointerException();
        if (count == a.length) {
            resize(2 * a.length);
        } else {
            if (tail == a.length - 1) {//尾指针到达数组末尾
                if (first != 0)//实际未满
                {
                    //指针重置为0
                    for (int i = 0; i < count; i++) {
                        a[i] = a[first + i];
                        a[first + i] = null;
                    }
                    first = 0;
                    tail = count - 1;
                }
            }
        }
        tail++;
        a[tail] = item;
        count++;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {//只有在数组真的满了的时候才进行扩充,所以first此时肯定为0
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < count && first + i <= tail; i++) {
            temp[i] = a[first + i];
        }
        a = temp;
    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("Underflow");
        Item item = a[first];
        a[first++] = null;
        count--;
        // shrink size of array if necessary
        if (count > 0 && count == a.length / 4) {
            //StdOut.print("*****" + item);
            resize(a.length / 2);
            first = 0;
            tail = count - 1;
        }
        return item;
    }

    public Item sample() {
        // return (but do not remove) a random item
        if (isEmpty()) throw new NoSuchElementException("Underflow");
        int pos = StdRandom.uniform(first, tail + 1);
        return a[pos];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private Item[] t;
        private int n;

        public ArrayIterator() {
            n = count;
            t = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                t[j] = a[j];
            }
        }

        @Override
        public boolean hasNext() {
            return n > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int pos = StdRandom.uniform(n);
            Item item = t[pos];
            t[pos] = t[n - 1];
            n--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // unit testing
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        //StdOut.println(randomizedQueue.size());
        for (int i = 1; i <= 3; i++) {
//            //StdOut.print("size: "+randomizedQueue.size());
            StdOut.println(randomizedQueue.dequeue());
            //StdOut.println(randomizedQueue.sample());
        }
        randomizedQueue.enqueue("enter");
        randomizedQueue.enqueue("this");
        randomizedQueue.enqueue("str");
        for (String item : randomizedQueue)
            StdOut.print(item + ",");
    }
}
