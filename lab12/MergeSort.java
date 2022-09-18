import edu.princeton.cs.algs4.Queue;

import static org.junit.Assert.assertEquals;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */

    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> ans = new Queue<Queue<Item>>();

        while (!items.isEmpty()) {
            Queue<Item> q = new Queue<>();
            Item item = items.dequeue();
            q.enqueue(item);
            ans.enqueue(q);
        }
        return ans;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> ans = new Queue<>();

        while (!(q1.isEmpty()) || !(q2.isEmpty())) {
            ans.enqueue(getMin(q1, q2));
        }

        return ans;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Item goal;
        Queue<Item> newitem = new Queue<>();
        for (int i = 0; i < items.size(); i++) {
            goal = items.dequeue();
            items.enqueue(goal);
            newitem.enqueue(goal);
        }
        Queue<Queue<Item>> ans0 = new Queue<Queue<Item>>();
        Queue<Item> ans = new Queue<Item>();
        int size = newitem.size();
        ans0 = makeSingleItemQueues(newitem);
        ans = mergesortHelper(ans0, size).dequeue();
        return ans;
    }

    private static <Item extends Comparable> Queue<Queue<Item>> mergesortHelper
            (Queue<Queue<Item>> items, Integer size) {
        Queue<Queue<Item>> ans = new Queue<Queue<Item>>();
        Queue<Item> merge1 = new Queue<Item>();
        Queue<Item> merge2 = new Queue<Item>();
        for (int i = 0; i < size; i++) {
            ans = new Queue<Queue<Item>>();
            if (items.size() == 1) {
                ans.enqueue(items.dequeue());
            } else {
                while (!items.isEmpty()) {
                    merge1 = items.dequeue();
                    if (!items.isEmpty()) {
                        merge2 = items.dequeue();
                        ans.enqueue(mergeSortedQueues(merge1, merge2));
                    } else {
                        ans.enqueue(merge1);
                    }
                }
            }
            items = ans;
        }
        return ans;
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        Queue<String> ansqueue;
        ansqueue = mergeSort(students);
        assertEquals("Alice", students.dequeue());
        assertEquals("Vanessa", students.dequeue());
        assertEquals("Ethan", students.dequeue());
        assertEquals("Alice", ansqueue.dequeue());
        assertEquals("Ethan", ansqueue.dequeue());
        assertEquals("Vanessa", ansqueue.dequeue());


    }
}
