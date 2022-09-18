import edu.princeton.cs.algs4.Queue;

import static org.junit.Assert.assertEquals;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     * <p>
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item : q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /**
     * Returns a random item from the given queue.
     */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted A Queue of unsorted items
     * @param pivot    The item to pivot on
     * @param less     An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are less than the given pivot.
     * @param equal    An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are equal to the given pivot.
     * @param greater  An empty Queue. When the function completes, this queue will contain
     *                 all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(Queue<Item> unsorted, Item pivot, Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        Item goal;
        int size = unsorted.size();
        for (int i = 0; i < size; i += 1) {
            goal = unsorted.dequeue();
            if (goal.compareTo(pivot) < 0) {
                less.enqueue(goal);
            } else if (goal.compareTo(pivot) > 0) {
                greater.enqueue(goal);
            } else {
                equal.enqueue(goal);
            }
        }
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> quickSort(Queue<Item> items) {
        if (items.size() == 0) {
            return items;
        }
        Item goal;
        Queue<Item> newitem = new Queue<>();

        for (int i = 0; i < items.size(); i++) {
            goal = items.dequeue();
            items.enqueue(goal);
            newitem.enqueue(goal);
        }
        Queue<Queue<Item>> ans = new Queue<>();
        Queue<Queue<Item>> ans0 = new Queue<>();
        Queue<Item> goalenque = new Queue<>();
        Queue<Item> less;
        Queue<Item> equal;
        Queue<Item> greater;
        Queue<Item> goalend;

        Item pivot;
        ans.enqueue(newitem);
        for (int i = 0; i < items.size(); i += 1) {
            ans0 = new Queue<>();
            while (!ans.isEmpty()) {
                goalenque = ans.dequeue();
                less = new Queue<>();
                equal = new Queue<>();
                greater = new Queue<>();
                pivot = getRandomItem(goalenque);
                partition(goalenque, pivot, less, equal, greater);
                ans0.enqueue(less);
                ans0.enqueue(equal);
                ans0.enqueue(greater);

            }
            ans = ans0;
        }
        goalend = new Queue<>();
        while (!ans.isEmpty()) {
            goalend = catenate(goalend, ans.dequeue());
        }
        return goalend;
    }


    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        Queue<String> ansqueue;
        ansqueue = quickSort(students);
        assertEquals("Alice", students.dequeue());
        assertEquals("Vanessa", students.dequeue());
        assertEquals("Ethan", students.dequeue());
        assertEquals("Alice", ansqueue.dequeue());
        assertEquals("Ethan", ansqueue.dequeue());
        assertEquals("Vanessa", ansqueue.dequeue());


    }
}
