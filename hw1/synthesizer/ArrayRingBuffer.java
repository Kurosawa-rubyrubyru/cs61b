// todo: Make sure to make this class a part of the synthesizer package
package synthesizer;

//todo: Make sure to make this class and all of its methods public
//todo: Make sure to make this class extend AbstractBoundedQueue<t>

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];

        // todo: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // todo: Enqueue the item. Don't forget to increase fillCount and update last.
        if (capacity == fillCount) {
            throw new RuntimeException("full");
        }
        last = (last + 1) % capacity;
        fillCount += 1;
        if (last == 0) {
            rb[capacity - 1] = x;
        } else {

            rb[last - 1] = x;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // todo: Dequeue the first item. Don't forget to decrease fillCount and update
        if (fillCount == 0) {
            throw new RuntimeException("empty");
        }
        T ans = rb[first];
        first = (first + 1) % capacity;
        fillCount -= 1;
        return ans;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("empty");
        }
        return rb[first];
        // todo: Return the first item. None of your instance variables should change.
    }

    // todo: When you get to part 5, implement the needed code to support iteration.

    private class ArrayRingIterator implements Iterator<T> {
        private int start;
        private int now;

        public ArrayRingIterator() {
            start = first;
            now = first;
        }

        public boolean hasNext() {
            return (now != last);
        }

        public T next() {
            if (start + 1 > capacity) {
                now = 0;
                return rb[now];
            } else {
                now += 1;
                return rb[now + 1];
            }
        }
    }

    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }
}
