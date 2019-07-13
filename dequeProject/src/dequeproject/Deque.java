/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dequeproject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author nsalemu2019
 */
public class Deque<Item> implements Iterable<Item> {

    private Node firstNode;
    private Node lastNode;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirstNode = firstNode;
        firstNode = new Node(item);
        if (isEmpty()) {
            lastNode = firstNode;
        } else {
            firstNode.next = oldFirstNode;
            oldFirstNode.previous = firstNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLastNode = lastNode;
        lastNode = new Node(item);
        if (isEmpty()) {
            firstNode = lastNode;
        } else {
            lastNode.previous = oldLastNode;
            oldLastNode.next = lastNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node oldFirstNode = firstNode;
        firstNode = oldFirstNode.next;
        size--;
        if (isEmpty()) {
            lastNode = null;
        } else {
            firstNode.previous = null;
        }

        return oldFirstNode.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node oldLastNode = lastNode;
        lastNode = oldLastNode.previous;
        size--;
        if (isEmpty()) {
            firstNode = null;
        } else {
            lastNode.next = null;
        }

        return oldLastNode.item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node curNode = firstNode;

            @Override
            public boolean hasNext() {
                return curNode.next != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                curNode = curNode.next;
                return curNode.item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Method is not supported");
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private class Node {

        private Item item;
        private Node next;
        private Node previous;

        public Node(Item item) {
            this.item = item;
        }
    }
}
