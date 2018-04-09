/*
Program #2
Jeremy Krick
cssc0915
*/

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private int currentSize;
    private long modCounter;
    private Node<E> head;

    private class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        public Node(E obj) {
            this.data = obj;
            next = null;
            prev = null;
        }
    }

    public OrderedLinkedListPriorityQueue() {
        head = null;
        currentSize = 0;
        modCounter = 0;
    }

    public boolean insert(E object) {
        Node<E> newNode = new Node<E>(object);
        Node<E> prev = null, curr = head;
        if (head == null) head = newNode;
        else {
            while (curr != null && object.compareTo(curr.data) >= 0){
                prev = curr;
                curr = curr.next;
            }
            if (curr == null) prev.next = newNode;
            else if (prev == null) {
                newNode.next = head;
                head = newNode;
            }
            else {
                prev.next = newNode;
                newNode.next = curr;
            }
        }
        currentSize++;
        modCounter++;
        return true;
    }

    public E remove() {
        if (isEmpty()) return null;
        E tmp = head.data;
        head = head.next;
        currentSize--;
        modCounter++;
        return tmp;
    }

    public boolean delete(E obj) {
        Node<E> prev = null, curr = head;
        while (curr != null) {
            if (obj.compareTo(curr.data) == 0) {
                if (curr == head) head = head.next;
                else {
                    prev.next = curr.next;
                    curr = prev;
                }
                currentSize--;
                modCounter++;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public E peek() {
        if (isEmpty()) return null;
        return head.data;
    }

    public boolean contains(E obj) {
        Node<E> tmp = head;
        while(tmp != null) {
            if (tmp.data.compareTo(obj) == 0)
                return true;
            tmp = tmp.next;
        }
        return false;
    }

    public int size() {return currentSize;}

    public void clear() {
        head = null;
        currentSize = 0;
        modCounter++;
    }

    public boolean isEmpty() {return currentSize == 0;}

    public boolean isFull() {return false;}

    public Iterator<E> iterator() {return new IteratorHelper();}

    class IteratorHelper implements Iterator<E> {
        Node<E> iterPtr;
        long modCheck;

        public IteratorHelper() {
            iterPtr = head;
            modCheck = modCounter;
        }

        public boolean hasNext() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();
            return iterPtr != null;}

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E tmp = iterPtr.data;
            iterPtr = iterPtr.next;
            return tmp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}