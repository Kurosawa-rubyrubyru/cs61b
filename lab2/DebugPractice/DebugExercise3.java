/**
 * Created by jug on 1/22/18.
 */
public class DebugExercise3 {
    public static long countTurnips(In in) {
        long totalTurnips = 0;
        while (!in.isEmpty()) {
            String vendor = in.readString();
            String foodType = in.readString();
            double cost = in.readDouble();
            long numAvailable = in.readLong();
            if (foodType.equals("turnip")) {
                if (numAvailable > 0) {
                    long newTotal = totalTurnips + numAvailable;
                    totalTurnips = newTotal;
                } else {
                    continue;
                }
            }
            in.readLine();
        }
        return totalTurnips;
    }

    public static void main(String[] args) {
        In in = new In("foods.csv");
        System.out.println(countTurnips(in));
    }
}
