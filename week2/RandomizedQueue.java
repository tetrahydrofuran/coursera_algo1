import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
    /**
     * Randomness and constant amortized time requirement means using an Array implementation.
     * Based on ResizingArrayStackOfStrings()
     * <p>
     * It's called a queue, but it's really a Bag.
     * Option 1:
     * Use StdRandom.permutation() in order to pre-compute the dequeue order and avoid generating random numbers over
     * `null` spaces?
     * <p>
     * Option 2:
     * Use StdRandom.uniform() and a swap() function to move the created null to the end of the Array.
     */

    private Item[] items;
    private int N = 0;

    public RandomizedQueue() {
        //noinspection unchecked
        items = (Item[]) new Object[1];

    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        // Resize check
        if (N == items.length) {
            resize(2 * items.length);
        }
        items[N++] = item;

    }

    private void resize(int capacity) {
        //noinspection unchecked
        Item[] copy = (Item[]) new Object[capacity];  // Create array
        for (int i = 0; i < N; i++) { // Copy contents
            copy[i] = items[i];
        }

        items = copy;
    }

    public Item dequeue() {

        // Resize check
        if (N > 0 && N == items.length / 4) {
            resize(items.length / 2);
        }

        return items[0];  // Placeholder
    }
    
    private void swapDecrement(int index) {
        // Swaps index with first non-null (based upon N), decrements N to account compact the valid array fields.
    }

    public Item sample() {
        return items[0];  // Placeholder
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        @Override
        public boolean hasNext() {
            return false;  // Placeholder
        }

        @Override
        public Item next() {
            return null;  // Placeholder
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
