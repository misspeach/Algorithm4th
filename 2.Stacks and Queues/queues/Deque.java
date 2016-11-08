import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by shizhan on 16/11/7.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first, tail;
    private int n;

    private class Node {
        private Item item;
        private Node next;
        private Node pre;
    }

    public Deque() {
        // construct an empty deque
        first = null;
        tail = null;
        n = 0;
    }

    public boolean isEmpty() {
        // is the deque empty?
        if (first == null && tail == null)
            return true;
        return false;
    }

    public int size() {
        // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) throw new NullPointerException("the client attempts to add a null item");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.pre = null;
        if (oldFirst != null)
            oldFirst.pre = first;
        if (tail == null)
            tail = first;
        n++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null) throw new NullPointerException("the client attempts to add a null item");
        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        tail.pre = oldTail;
        tail.next = null;
        if (oldTail != null)
            oldTail.next = tail;
        if (first == null)
            first = tail;
        n++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("Underflow");
        Item item = first.item;
        if (first == tail) {
            tail = null;
        }
        first = first.next;
        if (first != null)
            first.pre = null;
        n--;
        return item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("Underflow");
        Item item = tail.item;
        if (first == tail)//only one item in the deque
            first = null;
        tail = tail.pre;
        if (tail != null)
            tail.next = null;
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListDeque();
    }

    private class ListDeque implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // unit testing
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            //StdOut.print(item);
            deque.addFirst(item);
        }
        StdOut.println(deque.size());
        while (!deque.isEmpty()){
            StdOut.print(deque.removeFirst()+" ");
        }
        StdOut.println();
        deque.addLast("enter");
        deque.addLast("this");
        for (String item : deque) {
            //String s=item;
            StdOut.println(item);
        }
    }
}
