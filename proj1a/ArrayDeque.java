public class ArrayDeque<T> {
    private class StuffNode {
        private T item;

        public StuffNode(T i) {
            item = i;
        }

    }

    private int first;
    private int last;
    private int size;
    private int max_size;
    private int standard;
    private StuffNode[] deque;
    private StuffNode[] new_deque;

    public ArrayDeque() {
        deque = (StuffNode[]) new Object[8];
        size = 0;
        max_size = 8;
        standard = max_size / 2;
    }

    public void addFirst(T x) {
        if (size == 0) {
            first = standard;
            last = standard;
        }
        int goal = standard;
        while (goal >= 0) {
            if (deque[goal] == null) {
                deque[goal] = new StuffNode(x);
                break;
            } else {
                goal -= 1;
            }
        }
        if (goal == -1) {
            ChangeSize(1);
        }
        size += 1;
        first -= 1;
    }

    public void addLast(T x) {
        if (size == 0) {
            first = standard;
            last = standard;
        }
        int goal = standard;
        while (goal < max_size) {
            if (deque[goal] == null) {
                deque[goal] = new StuffNode(x);
                break;
            } else {
                goal += 1;
            }
        }
        if (goal == max_size) {
            ChangeSize(1);
        }
        size += 1;
        last += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int goal = first; goal <= last; goal += 1) {
            System.out.print(deque[goal].item);
        }
    }

    public T get(int index) {
        return deque[first + index].item;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            first += 1;
            size -= 1;
            if ((double) size < 0.25 * (double) max_size) {
                ChangeSize(0);
            }
            return deque[first - 1].item;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            last -= 1;
            size -= 1;
            if ((double) size < 0.25 * (double) max_size) {
                ChangeSize(0);
            }
            return deque[last + 1].item;
        }
    }

    private void ChangeSize(int p) {
        if (p == 1) {
            new_deque = (StuffNode[]) new Object[2 * max_size];
            deque[standard] = new_deque[2 * standard];
            for (int pos = 1; pos <= last - standard; pos += 1) {
                deque[standard + pos] = new_deque[2 * standard + pos];
            }
            for (int pos = 1; pos <= standard - first; pos += 1) {
                deque[standard - pos] = new_deque[2 * standard - pos];
            }
        } else if (p == 0) {
            new_deque = (StuffNode[]) new Object[max_size / 2];
            deque[standard] = new_deque[standard / 2];
            for (int pos = 1; pos <= last - standard; pos += 1) {
                deque[standard + pos] = new_deque[standard / 2 + pos];
            }
            for (int pos = 1; pos <= standard - first; pos += 1) {
                deque[standard - pos] = new_deque[standard / 2 - pos];
            }

        }

    }
}
