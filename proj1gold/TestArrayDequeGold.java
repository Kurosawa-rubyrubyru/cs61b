import static org.junit.Assert.assertEquals;

public class TestArrayDequeGold {
    public static void main(String[] args) {
        StudentArrayDeque<Integer> wr = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ri = new ArrayDequeSolution<>();
        int[] Operations = new int[100];
        int[] Numbers = new int[100];
        int size = 0;
        int NumOperation = 0;
        while (NumOperation < 100) {
            double RandomOne = StdRandom.uniform();
            double RandomTwo = StdRandom.uniform();
            if (RandomOne < 0.5) {
                size += 1;
                NumOperation += 1;
                if (RandomTwo < 0.5) {
                    wr.addLast(NumOperation);
                    ri.addLast(NumOperation);
                    Operations[NumOperation] = 1;
                    Numbers[NumOperation] = NumOperation;
                } else {
                    wr.addFirst(NumOperation);
                    ri.addFirst(NumOperation);
                    Operations[NumOperation] = 2;
                    Numbers[NumOperation] = NumOperation;
                }
            } else {
                if (size > 0) {
                    NumOperation += 1;
                    size -= 1;
                    if (RandomTwo < 0.5) {
                        Integer w = wr.removeLast();
                        Integer r = ri.removeLast();
                        Operations[NumOperation] = 3;
                        Numbers[NumOperation] = 0;
                        assertEquals(GetString(Operations, Numbers, NumOperation), w, r);

                    } else {
                        Integer w = wr.removeFirst();
                        Integer r = ri.removeFirst();
                        Operations[NumOperation] = 4;
                        Numbers[NumOperation] = 0;
                        assertEquals(GetString(Operations, Numbers, NumOperation), w, r);
                    }
                }
            }
        }
    }

    private static String GetString(int[] Operations, int[] Numbers, int NumOperation) {
        String AnsString = "";
        for (int i = 0; i < NumOperation; i += 1) {
            if (Operations[i] == 1) {
                String s = "addLast(" + Numbers[i] + ")\n";
                AnsString += s;
            } else if (Operations[i] == 2) {
                String s = "addFirst(" + Numbers[i] + ")\n";
                AnsString += s;
            } else if (Operations[i] == 3) {
                String s = "removeLast()\n";
                AnsString += s;
            } else {
                String s = "removeFirst()\n";
                AnsString += s;
            }
        }
        return AnsString;
    }
}
