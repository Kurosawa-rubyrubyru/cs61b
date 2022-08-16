public class ArrayDeque<T> {

    private int first;
    private int last;
    private int size;
    private int maxsize;
    private int standard;
    private T[] deque;
    private T[] newdeque;

    public ArrayDeque() {
        deque = (T[]) new Object[8];
        size = 0;
        maxsize = 8;
        standard = maxsize / 2;
    }

    public void addFirst(T x) {
        if (size == 0) {
            first = standard + 1;
            last = standard;
        }
        int goal = standard;
        while (goal >= 0) {
            if (deque[goal] == null) {
                deque[goal] = x;
                break;
            } else {
                goal -= 1;
            }
        }
        size += 1;
        first -= 1;
        if (goal <= 0) {
            changeSize(1);
        }

    }

    public void addLast(T x) {
        if (size == 0) {
            first = standard;
            last = standard - 1;
        }
        int goal = standard;
        while (goal < maxsize) {
            if (deque[goal] == null) {
                deque[goal] = x;

                break;
            } else {
                goal += 1;
            }
        }
        size += 1;
        last += 1;
        if (goal >= maxsize - 1) {
            changeSize(1);
        }

    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int goal = first; goal <= last - 1; goal += 1) {
            System.out.print(deque[goal] + " ");
        }
        System.out.print("\n");

    }

    public T get(int index) {
        return deque[first + index];
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T goal = deque[first];
            deque[first] = null;
            first += 1;
            size -= 1;
            if ((double) size < 0.25 * (double) maxsize && maxsize > 7) {
                changeSize(0);
            }
            return goal;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T goal = deque[last];
            deque[last] = null;
            last -= 1;
            size -= 1;
            if ((double) size < 0.25 * (double) maxsize && maxsize > 7) {
                changeSize(0);
            }
            return goal;
        }
    }

    private void changeSize(int p) {
        if (p == 1) {
            newdeque = (T[]) new Object[2 * maxsize];
            maxsize = maxsize * 2;
            newdeque[2 * standard] = deque[standard];
            for (int pos = 1; pos <= last - standard; pos += 1) {
                newdeque[2 * standard + pos] = deque[standard + pos];
            }
            for (int pos = 1; pos <= standard - first; pos += 1) {
                newdeque[2 * standard - pos] = deque[standard - pos];
            }
            first = 2 * standard - (standard - first);
            last = 2 * standard + (last - standard);
            standard = standard * 2;
            deque = newdeque.clone();
        } else if (p == 0) {
            newdeque = (T[]) new Object[maxsize / 2];
            maxsize = maxsize / 2;
            newdeque[standard / 2] = deque[standard];
//            for (int pos = 1; pos <= last - standard; pos += 1) {
//                newdeque[standard / 2 + pos] = deque[standard + pos];
//            }
//            for (int pos = 1; pos <= standard - first; pos += 1) {
//                newdeque[standard / 2 - pos] = deque[standard - pos];
//            }

            int newFirst = standard / 2 - (last - first) / 2;
            for (int pos = 0; pos < size; pos += 1) {
                newdeque[newFirst + pos] = deque[first + pos];
            }
            first = newFirst;
            last = newFirst + size - 1;
            standard = standard / 2;
            deque = newdeque.clone();
        }

    }
}
