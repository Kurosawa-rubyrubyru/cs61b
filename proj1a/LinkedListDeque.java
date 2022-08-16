public class LinkedListDeque<T> {
    private class StuffNode {
        private T item;
        private StuffNode next;
        private StuffNode prev;

        public StuffNode(T i, StuffNode prevv, StuffNode nextt) {
            item = i;
            next = nextt;
            prev = prevv;
        }
    }

    private StuffNode first;
    private StuffNode last;
    private StuffNode sentry;
    private int size;

    public LinkedListDeque() {
        sentry = new StuffNode(null, null, null);
        sentry.next = sentry;
        sentry.prev = sentry;
        size = 0;
    }

    /**
     * Adds x to the front of the list.
     */
    public void addFirst(T x) {
        first = new StuffNode(x, sentry, sentry.next);
        first.next.prev = first;
        sentry.next = first;
        if (size == 0) {
            last = first;
        }
        size += 1;
    }

    public void addLast(T x) {
        last = new StuffNode(x, sentry.prev, sentry);
        last.prev.next = last;
        sentry.prev = last;
        if (size == 0) {
            first = last;
        }
        size += 1;
    }


    /**
     * Adds an item to the end of the list.
     */


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            StuffNode goal = first;
            sentry.next = first.next;
            first.next.prev = sentry;
            first = first.next;
            size -= 1;
            return goal.item;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            StuffNode goal = last;
            sentry.prev = last.prev;
            last.prev.next = sentry;
            last = last.prev;
            size -= 1;
            return goal.item;
        }
    }

    public void printDeque() {
        StuffNode goal = first;
        for (int i = 0; i < size; i += 1) {
            System.out.print(goal.item);
            goal = goal.next;
        }
    }

    public T get(int index) {
        if (size == 0) {
            return null;
        } else if (index >= size) {
            return null;
        } else {
            StuffNode goal = first;
            for (int i = 0; i < index; i += 1) {
                goal = goal.next;
            }
            return goal.item;
        }
    }

    public T getRecursive(int index) {
        if (size == 0) {
            return null;
        } else if (index >= size) {
            return null;
        } else {
            StuffNode goal = first;
            for (int i = 0; i < index; i += 1) {
                goal = goal.next;
            }
            return getR(index, first).item;
        }
    }

    private StuffNode getR(int index, StuffNode now) {
        if (index == 0) {
            return now;
        }
        now = now.next;
        return getR(index - 1, now);
    }
}
