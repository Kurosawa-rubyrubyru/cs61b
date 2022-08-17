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
            first = standard;
            last = standard;
            deque[standard] = x;
            size = 1;
        } else {
            if (first == 0) {
                if (last == maxsize - 1) {
                    changeSize(1);
                    deque[first - 1] = x;
                    first -= 1;
                    size += 1;
                } else {
                    rePosition(1);
                    deque[first - 1] = x;
                    first -= 1;
                    size += 1;
                }
            } else {
                deque[first - 1] = x;
                first -= 1;
                size += 1;
            }
        }

    }

    public void addLast(T x) {
        if (size == 0) {
            first = standard;
            last = standard;
            deque[standard] = x;
            size = 1;
        } else {
            if (last == maxsize - 1) {
                if (first == 0) {
                    changeSize(1);
                    deque[last + 1] = x;
                    last += 1;
                    size += 1;
                } else {
                    rePosition(0);
                    deque[last + 1] = x;
                    last += 1;
                    size += 1;
                }
            } else {
                deque[last + 1] = x;
                last += 1;
                size += 1;
            }
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
            if ((double) size < (0.5 * (double) maxsize - 2.0) && maxsize > 7) {
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

    private void rePosition(int p) {
        newdeque = (T[]) new Object[maxsize];
        int dp = maxsize - size;
        int newFirst;
        if (p != 0) {
            newFirst = Math.max(dp / 2, 1);
        } else {
            newFirst = dp / 2;
        }
        for (int pos = 0; pos < size; pos += 1) {
            newdeque[newFirst + pos] = deque[first + pos];
        }
        first = newFirst;
        last = newFirst + size - 1;
        deque = newdeque.clone();
    }
}
