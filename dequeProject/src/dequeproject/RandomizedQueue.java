/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dequeproject;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author nsalemu2019
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] itemList;
    private int[] availableSpaces;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemList = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size >= itemList.length) {
            resizeArray(itemList.length * 2);
        }
        itemList[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size <= itemList.length / 4) {
            resizeArray(itemList.length * 2);
        }
        int randIndex = StdRandom.uniform(size);
        Item itemToBeRemoved = itemList[randIndex];
        itemList[randIndex] = itemList[--size];
        itemList[size] = null;

        return itemToBeRemoved;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return itemList[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resizeArray(int desiredLength) {
        Item[] newItemList = (Item[]) new Object[desiredLength];
        for (int i = 0; i < itemList.length; i++) {
            newItemList[i] = itemList[i];
        }
        itemList = newItemList;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int index = 0;
        Item[] shuffledItems;

        private RandomizedQueueIterator() {
            shuffledItems = (Item[]) new Object[size];
            for (int i = 0; i < shuffledItems.length; i++) {
                shuffledItems[i] = itemList[i];
            }
            StdRandom.shuffle(shuffledItems);
        }

        @Override
            public boolean hasNext() {
                return index<shuffledItems.length;
            }

            @Override
            public Item next() {
                if (isEmpty()) {
                    throw new NoSuchElementException();
                }
                return shuffledItems[index++];
            }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Method is not supported");
        }
    }
}
