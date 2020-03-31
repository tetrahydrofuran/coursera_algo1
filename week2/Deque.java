import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque() {
        /**
         * Constant wost case time means linked list implementation.
         * Explicitly track deque size to not have to traverse list in determining size.
         */

    }

    private void isValidArgument(Item item) {
        // Checks null argument or other conditions that may be imposed
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        isValidArgument(item);
        Node oldFirst = first; // Track old first

        // Create new node and link
        Node n = new Node();
        n.item = item;
        n.prev = null;
        n.next = oldFirst;

        if (size == 0) {
            // First insert, the first node is also the last node.
            last = n;
        } else {
            // Update the reverse link
            oldFirst.prev = n;
        }

        first = n;  // Set first reference properly
        size++;  // Increment to keep track of size
    }

    public void addLast(Item item) {  // same as addFirst, but with flipped directionality.
        isValidArgument(item);
        Node oldLast = last;

        Node n = new Node();
        n.item = item;
        n.next = null;
        n.prev = oldLast;

        if (size == 0) {
            first = n;
        } else {
            oldLast.next = n;
        }
        last = n;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item thing = first.item;  // Retrieve item
        size--;  // Decrement size and adjust pointer
        first = first.next;  // If size is zero, this should properly point to null
        first.prev = null;  // Update reverse direction
        if (size == 0) {
            last = null;  // Handle last case is null as well
        }

        return thing;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item thing = last.item;
        size--;
        last = last.prev;
        last.next = null;
        if (size == 0) {
            first = null;
        }

        return thing;

    }

    public Iterator<Item> iterator() {
        return new FrontOutIterator();
    }

    private class FrontOutIterator implements Iterator<Item> {
        /**
         * Iterates from front to back.  Same as Stack Iterator from the slides.
         */

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item thing = current.item;
            current = current.next;
            return thing;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // I'm not a Java person, so forgive the style.
        // Not really sure best way to unit test natively in Java
        Deque<Integer> deque = new Deque<>();

        // region Exceptions
        // No Elements
        try {
            deque.removeFirst();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("removeFirst() exception successfully thrown.");
        }

        try {
            deque.removeLast();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("removeLast() exception successfully thrown.");
        }

        // Insert Null
        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println("addFirst() exception successfully thrown.");
        }

        try {
            deque.addLast(null);
        } catch (IllegalArgumentException e) {
            System.out.println("addLast() exception successfully thrown.");
        }
        // endregion

        System.out.println(deque.isEmpty());
        System.out.println(deque.size() == 0);
        // 8 3 5 10 9
        deque.addFirst(5);
        deque.addFirst(3);
        deque.addFirst(8);
        deque.addLast(10);
        deque.addLast(9);
        System.out.println(deque.size() == 5);
        System.out.println(!deque.isEmpty());

        System.out.println(deque.removeLast() == 9);
        System.out.println(deque.removeFirst() == 8);
        System.out.println(deque.size() == 3);

        // 3 5 10
        for (Integer i : deque) {
            System.out.println(i);
        }

    }
}
