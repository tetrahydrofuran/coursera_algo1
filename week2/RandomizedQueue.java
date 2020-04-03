import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
public class RandomizedQueue<Item> implements Iterable<Item> {


    private Item[] items;
    private int n = 0;

    public RandomizedQueue() {
        // noinspection unchecked
        items = (Item[]) new Object[1];

    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // Resize check
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;

    }

    private void resize(int capacity) {
        // noinspection unchecked
        Item[] copy = (Item[]) new Object[capacity];  // Create array
        for (int i = 0; i < n; i++) { // Copy contents
            copy[i] = items[i];
        }

        // Temporary logging
        // System.out.println("Items:" + Integer.toString(N) + " | New Capacity: " + Integer.toString(capacity));
        items = copy;
    }

    public Item dequeue() {
        checkEmpty();
        int idx = StdRandom.uniform(n);
        Item item = items[idx];  // Obtain the item at index
        decrementSwap(idx);  // Deallocate and move null to end
        // Resize check
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    private void checkEmpty() {
        // Wrapper for isEmpty(), throw
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Deallocate index, swaps index with last non-null (based upon N), decrements N to account compact the valid
     * array fields.
     */
    private void decrementSwap(int index) {
        // N would normally naturally point to the null, so decrement before swap
        n--;
        items[index] = items[n];
        items[n] = null;
    }

    public Item sample() {
        checkEmpty();
        return items[StdRandom.uniform(n)];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        @Override
        public boolean hasNext() {
            return !isEmpty();  // Placeholder
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return dequeue();  // Placeholder
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        try {
            rq.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("deque() exception successfully thrown.");
        }

        try {
            rq.sample();
        } catch (NoSuchElementException e) {
            System.out.println("sample() exception successfully thrown.");
        }

        // Insert Null
        try {
            rq.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println("enqueue() exception successfully thrown.");
        }

        // endregion

        System.out.println(rq.isEmpty());
        System.out.println(rq.size() == 0);
        // 8 3 5 10 9
        rq.enqueue(5);
        rq.enqueue(3);
        rq.enqueue(8);
        rq.enqueue(10);
        rq.enqueue(9);
        System.out.println(rq.size() == 5);
        System.out.println(!rq.isEmpty());

        System.out.println("Sampling.");
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.size() == 5);  // Check size preserved.

        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        // One Item remaining

        // Check sample working
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.size() == 1);
        System.out.println(rq.dequeue());
        System.out.println(rq.isEmpty());

        // Iterable test
        rq.enqueue(5);
        rq.enqueue(3);
        rq.enqueue(8);
        rq.enqueue(10);
        rq.enqueue(9);

        for (Integer i : rq) {
            System.out.println(i);
        }
    }
}
